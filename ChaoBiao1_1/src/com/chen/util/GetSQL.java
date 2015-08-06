package com.chen.util;
/**
 * 主要功能：SQL语句编写
 * @author chen
 *
 */
public class GetSQL {
	//表创建的SQL语句
	private String createAdmin = 
			"CREATE TABLE admin(_id INTEGER PRIMARY KEY, name TEXT,	password TEXT);";

	private String createCustomer
	="CREATE TABLE customer(" +
			"_id INTEGER PRIMARY KEY," +
			"name TEXT," +
			"watId INTEGER," +
			"isreadwatch INTEGER," +
			"areanum INTEGER," +
			"biotopenum INTEGER," +
			"buildingnum INTEGER," +
			"unitnum INTEGER," +
			"levernum INTEGER," +
			"doorplatenum INTEGER," +
			"live INTEGER);";
	private String createIndustryuser = 
			"CREATE TABLE industryuser(" +
					"_id INTEGER PRIMARY KEY, " +
					"name TEXT," +
					"watId INTEGER," +
					"isreadwatch INTEGER," +
					"areanum INTEGER," +
					"biotopenum INTEGER," +
					"live INTEGER);";
	private String createWatch =
			"CREATE TABLE watch(" +
					"_id INTEGER PRIMARY KEY," +
					"num INTEGER," +
					"aircomid INTEGER," +
					"moneyleave REAL," +
					"baseUse REAL," +
					"totaluse REAL," +
					"time TEXT," +
					"state TEXT," +
					"frequency INTEGER," +
					"live INTEGER);";

	private String createAircomsumption =
			"CREATE TABLE aircomsumption(" +
					"_id INTEGER PRIMARY KEY," +
					"one REAL," +
					"two REAL," +
					"three REAL," +
					"four REAL," +
					"five REAL," +
					"six REAL," +
					"seven REAL," +
					"eight REAL," +
					"night REAL," +
					"ten  REAL," +
					"eleven REAL," +
					"twelve REAL," +
					"live INTEGER)";

	private String createArea = 
			"CREATE TABLE area(_id INTEGER PRIMARY KEY,name TEXT);";
	private String createBiotope = 
			"CREATE TABLE biotope(_id INTEGER PRIMARY KEY,name TEXT,area INTEGER);";
	private String createBuilding = 
			"CREATE TABLE building(_id INTEGER PRIMARY KEY,num INTEGER,biotope INTEGER);";
	private String createUnit = 
			"CREATE TABLE unit(_id INTEGER PRIMARY KEY,num INTEGER,building INTEGER);";
	private String createLever = 
			"CREATE TABLE lever(_id INTEGER PRIMARY KEY,num INTEGER,unit INTEGER);";
	//数据插入的sql语句
	private String insertAdmin = "INSERT INTO admin(name,password) values(?,?);";
	private String insertCustomer = "INSERT INTO customer(name,watId,isreadwatch,areanum,biotopenum,buildingnum,unitnum,levernum,doorplatenum,live) values(?,?,?,?,?,?,?,?,?,?);";
	private String insertIndustryuser
	="INSERT INTO industryuser(name,watId,isreadwatch,areanum,biotopenum,live) values(?,?,?,?,?,?);";
	private String insertWatch
	="INSERT INTO watch(num,aircomid,moneyleave,baseUse,totaluse,time,state,frequency,live) values(?,?,?,?,?,?,?,?,?);";
	private String insertAircomsumption =
			"INSERT INTO aircomsumption(one,two,three,four,five,six,seven,eight,night,ten,eleven,twelve,live) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
	
	private String insertArea = "INSERT INTO area(name) VALUES(?);";
	private String insertBiotope = "INSERT INTO biotope(name,area) VALUES(?,?);";
	private String insertBuilding = "INSERT INTO building(num,biotope) VALUES(?,?);";
	private String insertUnit = "INSERT INTO unit(num,building) VALUES(?,?);";
	private String insertLever = "INSERT INTO lever(num,unit) VALUES(?,?);";
	private String DATABASE_NAME = "Test.db";
	
	//查询area（区域）
	private String selArea = "SELECT * FROM area";
	private String selCus = "SELECT * FROM customer";
	
	//工业建档的SQL
	
	//查询指定地域的工业用户的Sql语句
	String listSql = "SELECT i._id,w._id,ai._id,w.num 表名,i.name 户主  " +
			"FROM industryuser i,watch w,aircomsumption ai " +
			"WHERE i.watId=w._id  AND w.aircomid=ai._id AND i.live=1 AND w.live=1 AND ai.live=1 " +
			"AND i.areanum=? AND i.biotopenum=?;";
	String updateAircom = "update aircomsumption set live =? where _id=?";
	String updateWatch = "update watch set live =? where _id=?";
	String updateIndustryuser = "update industryuser set live =? where _id=?";
	
	/****************************民用建档SQL**************************************/

	//查询小区Id，看是否存在
	String qBiotopeId = "SELECT b._id" +
			" FROM area a,biotope bp,building b" +
			" WHERE a._id=bp.area AND" +
			" bp._id=b.biotope AND" +
			" a._id=? AND bp._id=? AND b.num=?";
	
	String qUnitId = "SELECT u._id" +
			" FROM area a,biotope bp,building b,unit u" +
			" WHERE a._id=bp.area AND" +
			" bp._id=b.biotope AND" +
			" b._id=u.building AND" +
			" a._id=? AND bp._id=? AND" +
			" b.num=? AND u.num=?";
	String qLeverId = "SELECT l._id " +
			"FROM area a,biotope bp,building b,unit u,lever l" +
			" WHERE a._id=bp.area AND" +
			" bp._id=b.biotope AND" +
			" b._id=u.building AND" +
			" u._id=l.unit AND" +
			" a._id=? AND bp._id=? AND" +
			" b.num=? AND u.num=? AND" +
			" l.num=?";
	String listSqlCustomer = "SELECT c._id,w._id,ai._id,c.doorplatenum,w.num wat_num,b.num bio_num,u.num uni_num,l.num lea_num,c.name,w.state  " +
			"FROM customer c,watch w,building b,unit u,lever l,aircomsumption ai " +
			"WHERE c.watId=w._id AND c.buildingnum=b._id AND w.aircomid=ai._id " +
			"AND c.unitnum=u._id AND c.levernum=l._id AND w.live=1 AND c.live=1 " +
			"AND c.areanum=? AND c.biotopenum=?;";
	String updateCustomer = "update customer set live =? where _id=?";
	/****************************民用建档SQL**************************************/
	
	/****************************民用无线传输SQL**************************************/
	String qBuildNum = 
			"select building.num" +
			" FROM area,biotope,building" +
			" WHERE biotope.area = area._id AND biotope._id=building.biotope " +
			"AND area._id=? AND biotope._id=?" ;
	String qUnitNum = 
			"select unit.num" +
			" FROM unit,area,biotope,building" +
			" WHERE area._id=biotope.area AND biotope._id = building.biotope AND building._id=unit.building " +
			"AND area._id=? AND biotope._id=? AND building._id=? ;" ;
	String qCustomerBy ="SELECT DISTINCT c._id,w._id,a._id,b._id,bu._id,u._id,l._id, c.doorplatenum,c.name,w.num,w.state,c.isreadwatch " +
			"FROM customer c, watch w , area a, biotope b, building bu,unit u,lever l " +
			"WHERE c.live = 1 AND c.watId = w._id AND  a._id = c.areanum AND  b._id = c.biotopenum " +
			"AND b.area = a._id AND bu._id = c.buildingnum AND bu.biotope = b._id " +
			"AND l.num = c.levernum AND u._id = c.unitnum AND u.building = bu._id " +
			"AND l.unit = u._id AND a._id = ? AND b._id = ? AND bu._id = ? " +
			"AND u._id = ? AND l.num = ? ";
	String updateOpenValue = "update watch set state = '开阀' where _id = ?";
	String updateCloseValue = "update watch set state = '关阀' where _id = ?";
	String findAirUse = "SELECT air.* FROM watch w,aircomsumption air WHERE air._id=w.aircomid AND w._id = ?";
	String qAllCusMess ="SELECT DISTINCT bu.num,u.num,l.num,c.doorplatenum,c.name,w.num,w.totaluse,w.time,w.state,w.frequency " +
			"FROM customer c, watch w , area a, biotope b, building bu,unit u,lever l " +
			"WHERE c.watId = w._id AND  a._id = c.areanum AND  b._id = c.biotopenum " +
			"AND b.area = a._id AND bu._id = c.buildingnum AND bu.biotope = b._id " +
			"AND l.num = c.levernum AND u._id = c.unitnum AND u.building = bu._id " +
			"AND l.unit = u._id AND a._id = ? AND b._id = ? AND bu._id = ? AND u._id = ? " +
			"AND l.num = ? AND c._id = ?;";
	String qCusUnRead ="SELECT DISTINCT c._id,w._id,a._id,b._id,bu._id,u._id,l._id, c.doorplatenum,c.name,w.num,w.state,c.isreadwatch " +
			"FROM customer c, watch w , area a, biotope b, building bu,unit u,lever l " +
			"WHERE c.isreadwatch = 0 AND c.live=1 AND c.watId = w._id AND  a._id = c.areanum AND  b._id = c.biotopenum " +
			"AND b.area = a._id AND bu._id = c.buildingnum AND bu.biotope = b._id " +
			"AND l.num = c.levernum AND u._id = c.unitnum AND u.building = bu._id " +
			"AND l.unit = u._id AND a._id = ? AND b._id = ? AND bu._id = ? " +
			"AND u._id = ? AND l.num = ? ";

	/******************************工业无线传输************************************/
	String qIndAll ="SELECT DISTINCT i._id,a._id,b._id,w._id ,i.name,w.num,w.state,i.isreadwatch   " +
			"FROM industryuser i, watch w , area a, biotope b " +
			"WHERE i.live = 1 AND i.areanum = a._id AND i.biotopenum = b._id AND i.watId = w._id  " +
			"AND b.area = a._id AND a._id = ? AND b._id = ? ;";
	String qIndUnRead ="SELECT DISTINCT i._id,a._id,b._id,w._id ,i.name,w.num,w.state,i.isreadwatch   " +
			"FROM industryuser i, watch w , area a, biotope b " +
			"WHERE i.isreadwatch = 0 AND i.live = 1 AND i.areanum = a._id AND i.biotopenum = b._id AND i.watId = w._id  " +
			"AND b.area = a._id AND a._id = ? AND b._id = ? ;";



	/********************************工业无线传输**********************************/
	public String getqIndUnRead() {
		return qIndUnRead;
	}
	public String getqIndAll() {
		return qIndAll;
	}
	public String getqCusUnRead() {
		return qCusUnRead;
	}

	public String getqAllCusMess() {
		return qAllCusMess;
	}

	public String getFindAirUse() {
		return findAirUse;
	}

	public String getUpdateCloseValue() {
		return updateCloseValue;
	}

	public String getUpdateOpenValue() {
		return updateOpenValue;
	}

	public String getqCustomerBy() {
		return qCustomerBy;
	}
	public String getqUnitNum() {
		return qUnitNum;
	}
	public String getqBuildNum() {
		return qBuildNum;
	}
	/****************************民用无线传输SQL**************************************/
	
	public String getUpdateCustomer() {
		return updateCustomer;
	}
	public String getListSqlCustomer() {
		return listSqlCustomer;
	}
	public String getqLeverId() {
		return qLeverId;
	}
	public String getqUnitId() {
		return qUnitId;
	}
	public String getqBiotopeId() {
		return qBiotopeId;
	}
	public String getUpdateIndustryuser() {
		return updateIndustryuser;
	}
	public String getUpdateWatch() {
		return updateWatch;
	}
	public String getUpdateAircom() {
		return updateAircom;
	}
	public String getListSql() {
		return listSql;
	}
	public String getSelCus() {
		return selCus;
	}
	public String getSelArea() {
		return selArea;
	}
	public String getCreateAdmin() {
		return createAdmin;
	}
	public String getCreateCustomer() {
		return createCustomer;
	}
	public String getCreateIndustryuser() {
		return createIndustryuser;
	}
	public String getCreateWatch() {
		return createWatch;
	}
	public String getCreateAircomsumption() {
		return createAircomsumption;
	}
	public String getCreateArea() {
		return createArea;
	}
	public String getCreateBiotope() {
		return createBiotope;
	}
	public String getCreateLever() {
		return createLever;
	}
	public String getCreateUnit() {
		return createUnit;
	}
	public String getCreateBuilding() {
		return createBuilding;
	}
	
	public String getInsertAdmin() {
		return insertAdmin;
	}
	public String getInsertCustomer() {
		return insertCustomer;
	}
	public String getInsertIndustryuser() {
		return insertIndustryuser;
	}
	public String getInsertWatch() {
		return insertWatch;
	}
	public String getInsertAircomsumption() {
		return insertAircomsumption;
	}
	public String getInsertArea() {
		return insertArea;
	}
	public String getInsertBiotope() {
		return insertBiotope;
	}
	public String getInsertLever() {
		return insertLever;
	}
	public String getInsertUnit() {
		return insertUnit;
	}
	public String getInsertBuilding() {
		return insertBuilding;
	}
	public String getDATABASE_NAME() {
		return DATABASE_NAME;
	}
}
