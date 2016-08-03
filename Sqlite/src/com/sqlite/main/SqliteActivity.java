package com.sqlite.main;

import java.io.File;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SqliteActivity extends Activity {
	/** Called when the activity is first created. */
	
	//SQLiteDatabase 引用
	SQLiteDatabase mDb;
	SQLiteDatabaseDao dao;
	//显示结果
	TextView show;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//默认创建一个users.db的数据库
		dao=new SQLiteDatabaseDao();

		
		//创建一个数据库
		Button createDatabase=(Button)findViewById(R.id.createdatabase);
		createDatabase.setOnClickListener(createDatabaseClick);
		//获取所有数据库
		Button getDatabasesList=(Button)findViewById(R.id.getdatabaseslist);
		getDatabasesList.setOnClickListener(getDatabaseListClick);
		//重命名数据库
		Button renameDatabase=(Button)findViewById(R.id.renamedatabase);
		renameDatabase.setOnClickListener(renameDatabaseClick);
		//删除一个数据库
		Button removeDatabase=(Button)findViewById(R.id.removedatabase);
		removeDatabase.setOnClickListener(removeDatabaseClick);
	    //创建一个表
		Button createTable=(Button)findViewById(R.id.createtable);
		createTable.setOnClickListener(createTableClick);
		//获取所有的表
		Button getTablesList=(Button)findViewById(R.id.gettableslist);
		getTablesList.setOnClickListener(getTablesListClick);
		//重命名一个表
		Button renameTable=(Button)findViewById(R.id.renametable);
		renameTable.setOnClickListener(renameTableClick);
		//删除一个表
		Button dropTable=(Button)findViewById(R.id.droptable);
		dropTable.setOnClickListener(dropTableClick);
		//为表添加一个字段
		Button addTableColumn=(Button)findViewById(R.id.addtablecolumn);
		addTableColumn.setOnClickListener(addTableColumnClick);
		//获取表的所有列
		Button getTableColumnsList=(Button)findViewById(R.id.gettablecolumnslist);
		getTableColumnsList.setOnClickListener(getTableColumnsListClick);
		//插入一条数据
		Button insertTable=(Button)findViewById(R.id.inserttable);
		insertTable.setOnClickListener(insertTableClick);
		//查询一条数据
		Button queryTable=(Button)findViewById(R.id.querytable);
		queryTable.setOnClickListener(queryTableClick);
		//更新一条数据
		Button updateTable=(Button)findViewById(R.id.updatetable);
		updateTable.setOnClickListener(updateTableClick);
		//删除一条数据
		Button delete=(Button)findViewById(R.id.delete);
		delete.setOnClickListener(deleteClick);
		//显示结果
		show=(TextView)findViewById(R.id.showresult);
		
		
	}
	
	/************对按钮事件进行操作的事件响应****************/
	
	//创建一个数据库
	OnClickListener createDatabaseClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//创建一个名为students.db的数据库,主要是生成另外一个数据库以示区别
			openOrCreateDatabase("students.db",
					SQLiteDatabase.CREATE_IF_NECESSARY, null);
			show.setText("创建的数据库路径为\n"
					+getDatabasePath("students.db"));
			
		}
	};
	
	
	//创建一个应用程序数据库的个数(list)的事件响应
	OnClickListener getDatabaseListClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String []dblist=dao.getDatabasesList();
			String rs="";
			for(String s:dblist){				
				rs+=s+"\n";
			}
			show.setText("数据库名称为:\n"+ rs);
						
		}
	};
	
	
	//重命名一个数据库的事件响应
	OnClickListener renameDatabaseClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//创建一个data.db的数据库,并命名为renamedata.db数据库
			openOrCreateDatabase("data.db",
					SQLiteDatabase.CREATE_IF_NECESSARY, null);			
			File f = getDatabasePath("data.db");			
			File renameFile=getDatabasePath("renamedata.db");
			boolean b=f.renameTo(renameFile);
			if(b)
				show.setText("data.db已经重命名为renamedata.db");
			else
			
				show.setText("无法重命名");
		}
	};
	
	
	//删除一个数据库的事件响应
	OnClickListener removeDatabaseClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//删除students.db数据库
			dao.dropDatabase("students.db");
			//重新获取数据库名称
			String []dblist=dao.getDatabasesList();
			String rs="";
			for(String s:dblist){				
				rs+=s+"\n";
			}
			show.setText("数据库students.db已经删除\n现在数据库的名称为：\n"+rs);
		}
	};
	
	//创建一个表的事件响应
	
	OnClickListener createTableClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//在user.db数据库中插入mytable表,并添加相应的字段
			dao.createTable(mDb, "mytable");
			show.setText("数据库students.db已经创建mytable表\n");
			
		}
	};
	
	
	//获取一个数据库的所有表个数(list)的事件响应
	
	OnClickListener getTablesListClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//显示所有的表的数据
			String tableNames=dao.getTablesList(mDb);
			show.setText(tableNames);
			
		}
	};
	
	//重命名一个表的事件响应
	
	OnClickListener renameTableClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//创建一个testtable的表
			dao.createTable(mDb, "testtable");
			//将testtable重命名为newtable
			boolean b=dao.alterTableRenameTable(mDb, "testtable", "newtable");
			if(b)show.setText("testtable已经重命名为\nnewtable表\n");
			else show.setText("newtable已经存在\n请删除(drop table)后重试");
		}
	};
	
	//删除一个表的事件响应
	
	OnClickListener dropTableClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//删除	newtable表					
			dao.dropTable(mDb, "newtable");
			//显示所有的表的数据
			String tableNames=dao.getTablesList(mDb);
			show.setText("newtable已经删除\n现在表名称为:\n"+tableNames);			
		}
	};
	
	
	//修改一个表(给表添加一个字段)的事件响应
	
	OnClickListener addTableColumnClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//默认添加一个password字段,类型为varchar,长度为30
			boolean b=dao.alterTableAddColumn(mDb, "mytable", "password", " varchar(30)");
			if(b)show.setText("已经添加password字段\n字符类型为:varchar\n长度为:30");
			else show.setText("mytable表中password字段已经存在");
		}
	};
	
	//获取一个表的所有列的名称事件响应
	
	OnClickListener getTableColumnsListClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			String str=dao.getTableColumns(mDb);
			show.setText("mytable表的列名:\n"+str);			
		}
	};
	
	
	//对一个表添加一个数据的事件响应
	
	OnClickListener insertTableClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			User user=new User();
			user.setUsername("Mr.Young");
			user.setInfo("好学生");
			dao.insert(mDb, "mytable", user);
			
			Cursor c=dao.getAllData(mDb, "mytable");
			if(c.moveToLast()){			
				String id=c.getString(0);			
				String username=c.getString(1);			
				String info=c.getString(2);
				
				show.setText("最新添加的一条数据:\n"+"id:"+id+"\nusername:"+username+"\ninfo:"+info);			
			}
			
		}
	};
	
	//查询一个表的所有数据记录的事件响应
	
	OnClickListener queryTableClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//默认查询mytable所有数据
			
			Cursor c=dao.getAllData(mDb, "mytable");			
			String s="";			
			int columnsSize=c.getColumnCount();			
			String []columns=c.getColumnNames();			
			String columnsName="";
			//获取表头
			for (String col : columns) {
				
				columnsName+=col+"\u0020 \u0020";
			}
			//获取表的内容
			while(c.moveToNext()){
				
				for(int i=0;i<columnsSize;i++){				
					s+=c.getString(i)+"\u0020 \u0020";
				}
				s+="<br>";
			}
			show.setText(Html.fromHtml("<h5>"+columnsName+"</h5>"+s));				
		}
	};
	
	//更新一个表的数据的事件响应
	
	OnClickListener updateTableClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Cursor c=dao.getAllData(mDb, "mytable");
			if(c.moveToFirst()){
			
				int first=Integer.valueOf(c.getString(0));
			
				//默认修改第一条记录			
				dao.update(mDb, "mytable", first, "Yong Ming", "学习成绩优异");			
				Cursor u=dao.queryById(mDb, "mytable", first);			
				u.moveToFirst();			
				show.setText("id为:"+first+"的记录已经修改:\nid:"+first+"\nusername:"+u.getString(1)+"\ninfo:"+u.getString(2));
			
			}else
				
				show.setText("没有要更新的记录！请添加数据后再作修改");	
		}
	};
	
	//删除一个表的一条数据的事件响应
	
	OnClickListener deleteClick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Cursor c=dao.getAllData(mDb, "mytable");
			if(c.moveToLast()){			
				int last=Integer.valueOf(c.getString(0));
			
			//默认删除最后一条记录
			boolean b=dao.delete(mDb, "mytable", last);
			if(b)
				show.setText("成功删除id为:\n"+last+"的记录！");			
			}
			else
				show.setText("没有要删除的记录！");	
		}
	};
		
	
	//退出时关闭数据库
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		mDb.close();
	}





	/*******************
	 * 
	 *  
	 *  
	 * 对Sqlite数据库进行操作的类
	 *  
	 *  
	 *  
	 * ****************/

	class SQLiteDatabaseDao {

		public SQLiteDatabaseDao(){
			mDb=openOrCreateDatabase("users.db",
					SQLiteDatabase.CREATE_IF_NECESSARY, null);
		}

		/************ 对数据库的操作 ***********************/

		// 获取所有数据库的名称
		public String[] getDatabasesList() {

			return databaseList();
		}

		// 创建一个数据库
		public void createDatabase(String db) {

			openOrCreateDatabase(db, SQLiteDatabase.CREATE_IF_NECESSARY, null);
		}

		// 删除一个数据库
		public void dropDatabase(String db) {

			try {
				deleteDatabase(db);

			} catch (SQLException e) {
				Toast.makeText(getApplicationContext(), "删除数据库失败",
						Toast.LENGTH_LONG).show();

			}
		}

		/************ 对数据库的表的属性添加修改操作 ***********************/

		// 获取某个数据库的表的名称
		public String getTablesList(SQLiteDatabase mDb) {

			Cursor c = mDb
					.rawQuery(
							"select name from sqlite_master where type='table' order by name",
							null);
			String str="";
			while (c.moveToNext()) {
				 str+=c.getString(0)+"\n";
				 
			}
			return "表的名称为:\n"+str;
		}

		// 创建一个表,默认创建一个username info字段的表,可以在后面的代码中添加相应的列
		public void createTable(SQLiteDatabase mDb, String table) {
			try {
				mDb.execSQL("create table if not exists "+table+" (id integer primary key autoincrement, "
						+ "username text not null, info text not null);");
			} catch (SQLException e) {
				Toast.makeText(getApplicationContext(), "数据表创建失败",
						Toast.LENGTH_LONG).show();
			}
		}

		// 删除一个表
		public void dropTable(SQLiteDatabase mDb, String table) {

			try {
				mDb.execSQL("drop table if exists " + table);

			} catch (SQLException e) {
				Toast.makeText(getApplicationContext(), "数据表删除失败",
						Toast.LENGTH_LONG).show();
			}

		}

		// 修改表--重命名表名
		public boolean alterTableRenameTable(SQLiteDatabase mDb, String oldTable,
				String newTableName) {
			try {
				mDb.execSQL("alter table " + oldTable + " rename to  "
						+ newTableName+";");

			} catch (SQLException e) {
				Toast.makeText(getApplicationContext(), "数据表重命名失败",
						Toast.LENGTH_LONG).show();
				return false;
			}

			return true;
		}

		// 修改表--添加一列
		// @table 需要修改的table名
		// @column 添加的列的名称
		// @type 列的类型,如text,varchar等
		public boolean alterTableAddColumn(SQLiteDatabase mDb, String table,
				String column, String type) {
			try {
				mDb.execSQL("alter table  " + table + " add column " + column
						+ type + " ;");

			} catch (SQLException e) {
				Toast.makeText(getApplicationContext(), "数据表添加失败",
						Toast.LENGTH_LONG).show();
				return false;
			}

			return true;
		}

		// 获取表的列的名称
		public String getTableColumns(SQLiteDatabase mDb) {

			Cursor c=dao.getAllData(mDb, "mytable");;
			String []columns=c.getColumnNames();
			String str="";
			for (String s : columns) {

				str+=s+"\n";

			}

			return str;
		}

		/************ 对数据库的表数据增删改查操作 ***********************/
		// 添加一条数据,默认只向username和info字段添加数据

		public long insert(SQLiteDatabase mDb,String table,User user) {

			ContentValues values = new ContentValues();
			values.put("username", user.getUsername());
			values.put("info", user.getInfo());
			return mDb.insert(table, null, values);
		}

		/*
		 * 
		 * 删除一条数据
		 */
		public boolean delete(SQLiteDatabase mDb,String table,int id) {

			String whereClause =  "id=?";
			String[] whereArgs = new String[] {String.valueOf(id)};
			try{
			mDb.delete(table, whereClause, whereArgs);
			}catch (SQLException e) {
				Toast.makeText(getApplicationContext(), "删除数据库失败",
						Toast.LENGTH_LONG).show();
				return false;
			}
			return true;
		}

		/*
		 * 
		 * 修改一条数据
		 */
		public void update(SQLiteDatabase mDb,String table,int id,String username,String info) {

			ContentValues values = new ContentValues();
			values.put("username", username);
			values.put("info", info);
			String whereClause = "id=?";
			String[] whereArgs = new String[] { String.valueOf(id) };
			mDb.update(table, values, whereClause, whereArgs);
		}

		public Cursor queryById(SQLiteDatabase mDb,String table,int id) {

			// 第一个参数String：表名
			// 第二个参数String[]:要查询的列名
			// 第三个参数String：查询条件
			// 第四个参数String[]：查询条件的参数
			// 第五个参数String:对查询的结果进行分组
			// 第六个参数String：对分组的结果进行限制
			// 第七个参数String：对查询的结果进行排序
			String[] columns = new String[] { "id", "username", "info" };
			String selection = "id=?";
			String[] selectionArgs = { String.valueOf(id) };
			String groupBy = null;
			String having = null;
			String orderBy = null;
			return mDb.query(table, columns, selection,
					selectionArgs, groupBy, having, orderBy);
		}

		public Cursor getAllData(SQLiteDatabase mDb,String table) {

			//遍历表所有数据
			return mDb.rawQuery("select * from "+table, null);
			
			
			/** 如果需要返回指定的列,则执行以下语句
			String[] columns = new String[] { "id","username", "info" };
			// 调用SQLiteDatabase类的query函数查询记录
			return mDb.query(table, columns, null, null, null, null,
					null);
					
			*/

		}

	}

}