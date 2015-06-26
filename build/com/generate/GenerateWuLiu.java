package com.generate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;

import com.generate.db.DbConnection;
import com.generate.vo.Express;
import com.utils.excel.InFileBean;

public class GenerateWuLiu {
	private static String provinceProvince = "D:/wuliu/province.txt";
	private static String provinceSplit = "D:/wuliu/provinceS.txt";
	private static String cityFile = "D:/wuliu/city.txt";
	private static String citySplit = "D:/wuliu/cityS.txt";
	private static String standXls = "D:/wuliu/广州始发快递时效表.xlsx";

	public static void readXls() {
		File file = new File(standXls);
		List<Express> dataList = new ArrayList<Express>();
		Vector<String> set = new Vector<String>();
		set.add("province");
		set.add("ems");
		set.add("yuantong");
		set.add("sf");
		set.add("zhongtong");
		InFileBean<Express> fileBean = InFileBean.parseData(set,
				InFileBean.excelType2007, Express.class, file);

		dataList = fileBean.fileDataList(1);
		List<Express> changeList = new ArrayList<Express>();

		List<String> expressNames = new ArrayList<String>();
		Express ex = dataList.get(0);
		expressNames.add(ex.getEms());
		expressNames.add(ex.getYuantong());
		expressNames.add(ex.getSf());
		expressNames.add(ex.getZhongtong());
		int row = 0;
		for (Express express : dataList) {
			if(row == 0){
				row++;
				continue;
			}
			if(StringUtils.isBlank(express.getProvince())){
				break;
			}
			
			int index = 0;
			for (String s : expressNames) {
				Express etmp = new Express();
				etmp.setProvince(express.getProvince());
				etmp.setExpress(s);
				String effTmp = "";
				if (index == 0) {
					effTmp = express.getEms();
				} else if (index == 1) {
					effTmp = express.getYuantong();

				} else if (index == 2) {
					effTmp = express.getSf();
				} else if (index == 3) {
					effTmp = express.getZhongtong();
				}
				etmp.setEffective(filterDay(effTmp));
				changeList.add(etmp);
				index++;
			}
			
			row++;
		}
		 DbConnection dbconn = new DbConnection();
		 Connection conn = dbconn.getConn();
		 QueryRunner qr = new QueryRunner();
			
		 for(Express express : changeList){
			 StringBuilder provinceSB = new StringBuilder();
			 provinceSB.append("INSERT INTO  express ");
			 provinceSB.append(" ( ");
			 provinceSB.append(" province, ");
			 provinceSB.append(" express, ");
			 provinceSB.append(" effective");
			 provinceSB.append(" )");
			 provinceSB.append(" VALUES ("); 
			 provinceSB.append("'"+express.getProvince()+"', ");
			 provinceSB.append(" '"+express.getExpress()+"', ");
			 provinceSB.append(" '"+express.getEffective()+"'");
			 provinceSB.append(" )");
			 System.out.println(provinceSB);
			 try{
				 qr.update(conn, provinceSB.toString());
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
	}

	private static Double filterDay(String eff) {
		String effective = eff.replace("天", "");
		return Double.valueOf(effective);
	}

	public static void readWriteDB() {
		try {
			File province = new File(provinceProvince);
			FileReader provincein = new FileReader(province);
			BufferedReader provincebr = new BufferedReader(provincein);

			File provinceS = new File(provinceSplit);
			FileReader provinceinS = new FileReader(provinceS);
			BufferedReader provincebrS = new BufferedReader(provinceinS);

			String provinceString = null;
			String provinceinSString = null;

			String end_place = "";
			String order_num = "";
			String order_proportion = "";
			String send_dsr = "";
			String send_dsr_compare = "";
			String sent_speed = "";
			String sent_compare = "";
			int start = 0;
			DbConnection dbconn = new DbConnection();
			Connection conn = dbconn.getConn();
			while ((provinceString = provincebr.readLine()) != null) {

				if ("合作服务商".equals(provinceString)) {
					StringBuilder provinceSB = new StringBuilder();
					provinceSB.append("INSERT INTO  dsr_data (");
					provinceSB.append("start_place, ");
					provinceSB.append("end_place, ");
					provinceSB.append("order_num, ");
					provinceSB.append("order_proportion, ");
					provinceSB.append("send_dsr, ");
					provinceSB.append("send_dsr_compare, ");
					provinceSB.append("sent_speed, ");
					provinceSB.append("sent_compare");
					provinceSB.append(")");
					provinceSB.append("VALUES (");
					provinceSB.append("'广州市',");
					provinceSB.append("'" + end_place + "', ");
					provinceSB.append("'" + order_num + "', ");
					provinceSB.append("'" + order_proportion + "', ");
					if (StringUtils.isEmpty(send_dsr)) {
						send_dsr = "0";
					}
					provinceSB.append("'" + send_dsr + "', ");
					provinceSB.append("'" + send_dsr_compare + "', ");
					provinceSB.append("'" + sent_speed + "', ");
					provinceSB.append("'" + sent_compare + "'");
					provinceSB.append(");");
					end_place = "";
					order_num = "";
					order_proportion = "";
					send_dsr = "";
					send_dsr_compare = "";
					sent_speed = "";
					sent_compare = "";
					start = 0;

					QueryRunner qr = new QueryRunner();
					qr.update(conn, provinceSB.toString());
					BigInteger dsrid = (BigInteger) qr.query(conn,
							"SELECT LAST_INSERT_ID()", new ScalarHandler(1));

					String express_name = "";
					String order_nums = "";
					String order_proportions = "";
					String send_dsrs = "";
					String send_dsr_compares = "";
					String sent_speeds = "";
					String sent_compares = "";
					int startSplit = 0;
					while ((provinceinSString = provincebrS.readLine()) != null) {
						provinceinSString = provinceinSString.replaceAll(
								"\\s*", "");
						if (provinceinSString.indexOf("|") == -1) {
							// 快递名称
							if (express_name.length() > 0
									&& express_name.indexOf("--") == -1) {
								// 构建sql
								StringBuilder provinceSplitSB = new StringBuilder();
								provinceSplitSB
										.append("INSERT INTO  dir_express (");
								provinceSplitSB.append("express_name, ");
								provinceSplitSB.append("order_num, ");
								provinceSplitSB.append("order_proportion, ");
								provinceSplitSB.append("send_dsr, ");
								provinceSplitSB.append("send_dsr_compare, ");
								provinceSplitSB.append("send_speed, ");
								provinceSplitSB.append("send_compare, ");
								provinceSplitSB.append("dsr_id");
								provinceSplitSB.append(")");
								provinceSplitSB.append("VALUES (");
								provinceSplitSB.append("'" + express_name
										+ "', ");
								provinceSplitSB
										.append("'" + order_nums + "', ");
								provinceSplitSB.append("'" + order_proportions
										+ "', ");
								provinceSplitSB.append("'" + send_dsrs + "', ");
								provinceSplitSB.append("'" + send_dsr_compares
										+ "', ");
								provinceSplitSB.append("'" + sent_speeds
										+ "', ");
								provinceSplitSB.append("'" + sent_compares
										+ "', ");
								provinceSplitSB.append("'" + dsrid.toString()
										+ "'");
								provinceSplitSB.append(");");
								qr.update(conn, provinceSplitSB.toString());
								System.out.println(provinceSplitSB.toString());
								startSplit = 0;
								express_name = "";
							}
							express_name = provinceinSString;
						} else {
							if (startSplit == 0) {
								String[] orders = provinceinSString
										.split("\\|");
								if (!"-".equals(orders[0])) {
									order_nums = orders[0];
								}
								if ("-".equals(orders[0])) {
									order_nums = "0";
								}
								if (!"-".equals(orders[1])) {
									order_proportions = orders[1];
									order_proportions = order_proportions
											.replaceAll("%", "");
								}
							} else if (startSplit == 1) {
								String[] orders = provinceinSString
										.split("\\|");
								if (!"-".equals(orders[0])) {
									send_dsrs = orders[0];
								}
								if ("-".equals(orders[0])) {
									send_dsrs = "0";
								}
								if (!"-".equals(orders[1])) {
									send_dsr_compares = orders[1];
									send_dsr_compares = send_dsr_compares
											.replaceAll("%", "");
									send_dsr_compares = send_dsr_compares
											.replace("低", "-");
									send_dsr_compares = send_dsr_compares
											.replace("高", "");
								}
								if ("-".equals(orders[1])) {
									send_dsr_compares = "0";
								}
							} else if (startSplit == 2) {
								String[] orders = provinceinSString
										.split("\\|");
								if (!"-".equals(orders[0])) {
									sent_speeds = processSpeed(orders[0]);

								}
								if ("-".equals(orders[0])) {
									sent_speeds = "0";
								}
								if (!"-".equals(orders[1])) {
									sent_compares = orders[1];
									sent_compares = sent_compares.replaceAll(
											"%", "");
									sent_compares = sent_compares.replace("低",
											"-");
									sent_compares = sent_compares.replace("高",
											"");
								}
								if ("-".equals(orders[1])) {
									sent_compares = "0";
								}
							}
							startSplit++;
						}
						if (provinceinSString.indexOf("--") != -1) {
							break;
						}
					}

					System.out.println(provinceSB.toString());
					System.out
							.println("----------------------------------------");

				} else {
					if (provinceString.indexOf("|") == -1) {
						end_place = provinceString;
					} else {
						provinceString = provinceString.replaceAll("\\s*", "");
						if (start == 0) {
							String[] orders = provinceString.split("\\|");
							if (!"-".equals(orders[0])) {
								order_num = orders[0];
							}
							if ("-".equals(orders[0])) {
								order_num = "0";
							}
							if (!"-".equals(orders[1])) {
								order_proportion = orders[1];
								order_proportion = order_proportion.replaceAll(
										"%", "");
							}
							if ("-".equals(orders[1])) {
								order_proportion = "0";
							}
						} else if (start == 1) {
							String[] orders = provinceString.split("\\|");
							if (!"-".equals(orders[0])) {
								send_dsr = orders[0];
							}
							if ("-".equals(orders[0])) {
								send_dsr = "0";
							}
							if (!"-".equals(orders[1])) {
								send_dsr_compare = orders[1];
								send_dsr_compare = send_dsr_compare.replaceAll(
										"%", "");
								send_dsr_compare = send_dsr_compare.replace(
										"低", "-");
								send_dsr_compare = send_dsr_compare.replace(
										"高", "");
							}
							if ("-".equals(orders[1])) {
								send_dsr_compare = "0";
							}
						} else if (start == 2) {
							String[] orders = provinceString.split("\\|");
							if (!"-".equals(orders[0])) {
								sent_speed = processSpeed(orders[0]);

							}
							if ("-".equals(orders[0])) {
								sent_speed = "0";

							}
							if (!"-".equals(orders[1])) {
								sent_compare = orders[1];
								sent_compare = sent_compare.replaceAll("%", "");
								sent_compare = sent_compare.replace("低", "-");
								sent_compare = sent_compare.replace("高", "");
							}
							if ("-".equals(orders[1])) {
								sent_compare = "0";

							}
						}
						start++;
					}
				}

			}

			provincebr.close();
			provincein.close();
			provincebrS.close();
			provinceinS.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String processSpeed(String time) {
		String sent_speed = time;
		sent_speed = sent_speed.replace("天", ".");
		sent_speed = sent_speed.replace("小时", "");
		if (sent_speed.contains(".")) {
			String[] times = sent_speed.split("\\.");

			double hourtmp = Integer.valueOf(times[1]);
			double dbHour = hourtmp / 24;
			double x = Double.parseDouble(dbHour * 100 + "");
			int hour = (int) x;
			return times[0] + "." + hour;
		} else {
			double dbHour = Integer.valueOf(sent_speed) / 24;
			double x = Double.parseDouble(dbHour * 100 + "");
			int hour = (int) x;
			return 0 + "." + hour;
		}

	}

	public static void readWriteCityDB() {
		try {
			File city = new File(cityFile);
			FileReader cityin = new FileReader(city);
			BufferedReader citybr = new BufferedReader(cityin);

			File cityS = new File(citySplit);
			FileReader cityinS = new FileReader(cityS);
			BufferedReader citybrS = new BufferedReader(cityinS);

			String cityString = null;
			String cityinSString = null;

			String end_place = "";
			String order_num = "";
			String order_proportion = "";
			String send_dsr = "";
			String send_dsr_compare = "";
			String sent_speed = "";
			String sent_compare = "";
			int start = 0;
			DbConnection dbconn = new DbConnection();
			Connection conn = dbconn.getConn();
			while ((cityString = citybr.readLine()) != null) {

				if ("合作服务商".equals(cityString)) {
					StringBuilder citySB = new StringBuilder();
					citySB.append("INSERT INTO  dsr_data (");
					citySB.append("start_place, ");
					citySB.append("end_place, ");
					citySB.append("order_num, ");
					citySB.append("order_proportion, ");
					citySB.append("send_dsr, ");
					citySB.append("send_dsr_compare, ");
					citySB.append("sent_speed, ");
					citySB.append("sent_compare,");
					citySB.append("city");
					citySB.append(")");
					citySB.append("VALUES (");
					citySB.append("'广州市',");
					citySB.append("'" + end_place + "', ");
					citySB.append("'" + order_num + "', ");
					citySB.append("'" + order_proportion + "', ");
					citySB.append("'" + send_dsr + "', ");
					citySB.append("'" + send_dsr_compare + "', ");
					citySB.append("'" + sent_speed + "', ");
					citySB.append("'" + sent_compare + "',");
					citySB.append("'1'");
					citySB.append(");");
					end_place = "";
					order_num = "";
					order_proportion = "";
					send_dsr = "";
					send_dsr_compare = "";
					sent_speed = "";
					sent_compare = "";
					start = 0;

					QueryRunner qr = new QueryRunner();
					qr.update(conn, citySB.toString());
					BigInteger dsrid = (BigInteger) qr.query(conn,
							"SELECT LAST_INSERT_ID()", new ScalarHandler(1));

					String express_name = "";
					String order_nums = "";
					String order_proportions = "";
					String send_dsrs = "";
					String send_dsr_compares = "";
					String sent_speeds = "";
					String sent_compares = "";
					int startSplit = 0;
					while ((cityinSString = citybrS.readLine()) != null) {
						cityinSString = cityinSString.replaceAll("\\s*", "");
						if (cityinSString.indexOf("|") == -1) {
							// 快递名称
							if (express_name.length() > 0
									&& express_name.indexOf("--") == -1) {
								// 构建sql
								StringBuilder citySplitSB = new StringBuilder();
								citySplitSB
										.append("INSERT INTO  dir_express (");
								citySplitSB.append("express_name, ");
								citySplitSB.append("order_num, ");
								citySplitSB.append("order_proportion, ");
								citySplitSB.append("send_dsr, ");
								citySplitSB.append("send_dsr_compare, ");
								citySplitSB.append("send_speed, ");
								citySplitSB.append("send_compare, ");
								citySplitSB.append("dsr_id");
								citySplitSB.append(")");
								citySplitSB.append("VALUES (");
								citySplitSB.append("'" + express_name + "', ");
								citySplitSB.append("'" + order_nums + "', ");
								citySplitSB.append("'" + order_proportions
										+ "', ");
								citySplitSB.append("'" + send_dsrs + "', ");
								citySplitSB.append("'" + send_dsr_compares
										+ "', ");
								citySplitSB.append("'" + sent_speeds + "', ");
								citySplitSB.append("'" + sent_compares + "', ");
								citySplitSB
										.append("'" + dsrid.toString() + "'");
								citySplitSB.append(");");
								qr.update(conn, citySplitSB.toString());
								System.out.println(citySplitSB.toString());
								startSplit = 0;
								express_name = "";
							}
							express_name = cityinSString;
						} else {
							if (startSplit == 0) {
								String[] orders = cityinSString.split("\\|");
								if (!"-".equals(orders[0])) {
									order_nums = orders[0];
								}
								if ("-".equals(orders[0])) {
									order_nums = "0";
								}
								if (!"-".equals(orders[1])) {
									order_proportions = orders[1];
									order_proportions = order_proportions
											.replaceAll("%", "");
								}
								if ("-".equals(orders[1])) {
									order_proportions = "0";
								}
							} else if (startSplit == 1) {
								String[] orders = cityinSString.split("\\|");
								if (!"-".equals(orders[0])) {
									send_dsrs = orders[0];
								}
								if ("-".equals(orders[0])) {
									send_dsrs = "0";
								}
								if (!"-".equals(orders[1])) {
									send_dsr_compares = orders[1];
									send_dsr_compares = send_dsr_compares
											.replaceAll("%", "");
									send_dsr_compares = send_dsr_compares
											.replace("低", "-");
									send_dsr_compares = send_dsr_compares
											.replace("高", "");
								}
								if ("-".equals(orders[1])) {
									send_dsr_compares = "0";
								}
							} else if (startSplit == 2) {
								String[] orders = cityinSString.split("\\|");
								if (!"-".equals(orders[0])) {
									sent_speeds = processSpeed(orders[0]);

								}
								if ("-".equals(orders[0])) {
									sent_speeds = "0";

								}
								if (!"-".equals(orders[1])) {
									sent_compares = orders[1];
									sent_compares = sent_compares.replaceAll(
											"%", "");
									sent_compares = sent_compares.replace("低",
											"-");
									sent_compares = sent_compares.replace("高",
											"");
								}
								if ("-".equals(orders[1])) {
									sent_compares = "0";
								}
							}
							startSplit++;
						}
						if (cityinSString.indexOf("--") != -1) {
							break;
						}
					}

					System.out
							.println("----------------------------------------");
					System.out.println(citySB.toString());
				} else {
					if (cityString.indexOf("|") == -1) {
						end_place = cityString;
					} else {
						cityString = cityString.replaceAll("\\s*", "");
						if (start == 0) {
							String[] orders = cityString.split("\\|");
							if (!"-".equals(orders[0])) {
								order_num = orders[0];
							}
							if (!"-".equals(orders[1])) {
								order_proportion = orders[1];
								order_proportion = order_proportion.replaceAll(
										"%", "");
							}
							if ("-".equals(orders[1])) {
								order_proportion = "0";
							}
						} else if (start == 1) {
							String[] orders = cityString.split("\\|");
							if (!"-".equals(orders[0])) {
								send_dsr = orders[0];
							}
							if ("-".equals(orders[0])) {
								send_dsr = "0";
							}
							if (!"-".equals(orders[1])) {
								send_dsr_compare = orders[1];
								send_dsr_compare = send_dsr_compare.replaceAll(
										"%", "");
								send_dsr_compare = send_dsr_compare.replace(
										"低", "-");
								send_dsr_compare = send_dsr_compare.replace(
										"高", "");
							}
							if ("-".equals(orders[1])) {
								send_dsr_compare = "0";
							}
						} else if (start == 2) {
							String[] orders = cityString.split("\\|");
							if (!"-".equals(orders[0])) {
								sent_speed = processSpeed(orders[0]);

							}
							if ("-".equals(orders[0])) {
								sent_speed = "0";
							}
							if (!"-".equals(orders[1])) {
								sent_compare = orders[1];
								sent_compare = sent_compare.replaceAll("%", "");
								sent_compare = sent_compare.replace("低", "-");
								sent_compare = sent_compare.replace("高", "");
							}
							if ("-".equals(orders[1])) {
								sent_compare = "0";
							}
						}
						start++;
					}
				}

			}

			citybr.close();
			cityin.close();
			citybrS.close();
			cityinS.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String args[]) {
		readXls();
	    readWriteDB();
		readWriteCityDB();
	//	test();
	}

	public static void test() {
		try {
			DbConnection dbconn = new DbConnection();
			Connection conn = dbconn.getConn();
			String sql = "select count(*) as num from base_module";
			QueryRunner qr = new QueryRunner();
			Long dsrid = (Long) qr.query(conn, sql,
					new ScalarHandler(1));
			System.out.println(dsrid);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
