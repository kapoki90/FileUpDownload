// FileDAO.java

package model.com.myproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FileDAO {
	
	private static FileDAO instance = new FileDAO();
	
	public static FileDAO getInstance() {
		return instance;
	}
	
	private FileDAO() {}
	
	public int insertFile(FileDTO dto) {
		int re = 0;
		Connection con = null;
		PreparedStatement ps = null;//fileboard_idx_seq.NEXTVAL
		String sql = "INSERT INTO fileboard(idx,ref,lev,seq,filename,fileExtension,filesize) "
				+ "VALUES(fileboard_idx_seq.NEXTVAL,fileboard_idx_seq.CURRVAL,0,0,?,?,?)";
		try {
			con = UtillDB.getCon(con);
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getFileName());
			ps.setString(2, dto.getFileExtension());
			ps.setLong(3, dto.getFileSize());
			re = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps);
		} // try - catch - finally
		return re;
	} // insertFile()
	//read first page
	public ArrayList<FileDTO> firstPageList() {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM fileboard where seq=0 and lev=0 order by ref desc";
		try {
			con = UtillDB.getCon(con); //get Connection object
			ps = con.prepareStatement(sql); //Creates a PreparedStatement object
			rs = ps.executeQuery();//Executes the SQL query and returns the ResultSet object
			while (rs.next()) { //read row
				FileDTO dto = new FileDTO(); // make dto object
				dto.setIdx(rs.getInt("idx")); //call setter method
				dto.setRef(rs.getInt("ref"));
				dto.setLev(rs.getInt("lev"));
				dto.setSeq(rs.getInt("seq"));
				dto.setFileName(rs.getString("filename"));
				dto.setFolderName(rs.getString("folderName"));
				dto.setFileExtension(rs.getString("fileExtension"));
				dto.setFileSize(rs.getLong("filesize"));
				dto.setWdate(rs.getString("wdate"));
				list.add(dto); //add 1 file infomation to Arraylist
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps, rs);
		} // try - catch - finally
		return list;
	} // listFile()
	
	public ArrayList<FileDTO> allPageList() {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM fileboard order by ref desc";
		try {
			con = UtillDB.getCon(con);//db�� �����Ѵ�
			ps = con.prepareStatement(sql);//sql�� �� PreparedStatement��ü�� ��´�.
			rs = ps.executeQuery();//sql�� ����
			while (rs.next()) {//sql�������� ���྿ �о�´�
				FileDTO dto = new FileDTO();//�ϳ��� ���������� ������ ��ü
				dto.setIdx(rs.getInt("idx"));
				dto.setRef(rs.getInt("ref"));
				dto.setLev(rs.getInt("lev"));
				dto.setSeq(rs.getInt("seq"));
				dto.setFileName(rs.getString("filename"));
				dto.setFolderName(rs.getString("folderName"));
				dto.setFileExtension(rs.getString("fileExtension"));
				dto.setFileSize(rs.getLong("filesize"));
				dto.setWdate(rs.getString("wdate"));
				list.add(dto);//ArrayList�� �������� ��ü �߰�
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps, rs);
		} // try - catch - finally
		return list;
	} // listFile()
	
	public int deleteFile(int idx) {
		int re = 0;
		Connection con = null;
		PreparedStatement ps = null;
		
		System.out.println("DELETE FROM fileboard where idx = "+idx);
		String sql = "DELETE FROM fileboard where idx = ?";
		try {
			con = UtillDB.getCon(con);
			ps = con.prepareStatement(sql);
			ps.setInt(1, idx);
			re = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps);
		} // try - catch - finally
		return re;
	} // insertFile()

	public void createFolder(FileDTO dto) {
		
		Connection con = null;
		PreparedStatement ps = null;//fileboard_idx_seq.NEXTVAL
		String sql = "INSERT INTO fileboard(idx,ref,lev,seq,folderName,fileExtension,filesize) "
				+ "VALUES(fileboard_idx_seq.NEXTVAL,fileboard_idx_seq.CURRVAL,0,0,?,?,?)";
		try {
			con = UtillDB.getCon(con);
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getFolderName());
			ps.setString(2, dto.getFileExtension());
			ps.setInt(3, 0);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps);
		} // try - catch - finally
	
	} // createFolder()
	
	public void createFolderInFolder(FileDTO dto,String NewfolderName) {
		
		Connection con = null;
		PreparedStatement ps = null;//fileboard_idx_seq.NEXTVAL
		String sql = "INSERT INTO fileboard(idx,ref,lev,seq,folderName,fileExtension,filesize) "
				+ "VALUES(fileboard_idx_seq.NEXTVAL,?,?,?,?,?,?)";
		try {
			con = UtillDB.getCon(con);
			ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getRef());
			ps.setInt(2, dto.getLev()+1);
			ps.setInt(3, dto.getSeq()+1);
			ps.setString(4, dto.getFolderName()+"/"+NewfolderName);
			ps.setString(5, dto.getFileExtension());
			ps.setInt(6, 0);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps);
		} // try - catch - finally
	
	} // createFolder()
	
	//해당 폴더의 내용을 읽어옴
	public ArrayList<FileDTO> listFolder(int ref,int lev) {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM fileboard where ref="+ref+"and lev="+lev+"+1 order by seq asc";
		try {
			con = UtillDB.getCon(con);//db�� �����Ѵ�
			ps = con.prepareStatement(sql);//sql�� �� PreparedStatement��ü�� ��´�.
			rs = ps.executeQuery();//sql�� ����
			while (rs.next()) {//sql�������� ���྿ �о�´�
				FileDTO dto = new FileDTO();//�ϳ��� ���������� ������ ��ü
				dto.setIdx(rs.getInt("idx"));
				dto.setRef(rs.getInt("ref"));
				dto.setLev(rs.getInt("lev"));
				dto.setSeq(rs.getInt("seq"));
				dto.setFileName(rs.getString("filename"));
				dto.setFolderName(rs.getString("folderName"));
				dto.setFileExtension(rs.getString("fileExtension"));
				dto.setFileSize(rs.getLong("filesize"));
				dto.setWdate(rs.getString("wdate"));
				list.add(dto);//ArrayList�� �������� ��ü �߰�
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps, rs);
		} // try - catch - finally
		return list;
	}

	public FileDTO selectByIdx(int idx) {
		FileDTO dto = new FileDTO();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM fileboard where idx="+idx;
		try {
			con = UtillDB.getCon(con);//db�� �����Ѵ�
			ps = con.prepareStatement(sql);//sql�� �� PreparedStatement��ü�� ��´�.
			rs = ps.executeQuery();//sql�� ����
			while (rs.next()) {//sql�������� ���྿ �о�´�
				dto.setIdx(rs.getInt("idx"));
				dto.setRef(rs.getInt("ref"));
				dto.setLev(rs.getInt("lev"));
				dto.setSeq(rs.getInt("seq"));
				dto.setFileName(rs.getString("filename"));
				dto.setFolderName(rs.getString("folderName"));
				dto.setFileExtension(rs.getString("fileExtension"));
				dto.setFileSize(rs.getLong("filesize"));
				dto.setWdate(rs.getString("wdate"));
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps, rs);
		} // try - catch - finally
		return dto;
	}

	public void insertFileToFolder(FileDTO dto) {
		Connection con=null,con2 = null;
		PreparedStatement ps=null,ps2 = null;//fileboard_idx_seq.NEXTVAL
		String sql = "INSERT INTO fileboard(idx,ref,lev,seq,filename,folderName,fileExtension,filesize) "
				+ "VALUES(fileboard_idx_seq.NEXTVAL,?,?,?,?,?,?,?)";
		
		String sql2 = "UPDATE fileboard SET filesize = filesize+"+dto.getFileSize()+" WHERE ref="+dto.getRef()+" and lev="+dto.getLev();
		
		try {
			con = UtillDB.getCon(con);
			ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getRef());
			ps.setInt(2, dto.getLev()+1);
			ps.setInt(3, dto.getSeq()+1);
			ps.setString(4, dto.getFileName());
			ps.setString(5, dto.getFolderName());
			ps.setString(6, dto.getFileExtension());
			ps.setLong(7, dto.getFileSize());
			ps.executeUpdate();
				
			con2 = UtillDB.getCon(con2);
			ps2 = con.prepareStatement(sql2);
			ps2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps);
			UtillDB.closeDB(con2, ps2);
		} // try - catch - finally
	}

	public void incrementSeq(int ref, int seq) {
		Connection con = null;
		PreparedStatement ps = null;//fileboard_idx_seq.NEXTVAL
		String sql = "update fileboard set seq =seq+1 where ref ="+ref+"and seq>"+seq;
		try {
			con = UtillDB.getCon(con);
			ps = con.prepareStatement(sql);
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps);
		} // try - catch - finally
	}

	public ArrayList<FileDTO> searchByFilename(String keyword) {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM fileboard where upper(filename) like '%'||upper('"+keyword+"')||'%' order by ref desc";
		
		try {
			con = UtillDB.getCon(con);//db�� �����Ѵ�
			ps = con.prepareStatement(sql);//sql�� �� PreparedStatement��ü�� ��´�.
			rs = ps.executeQuery();//sql�� ����
			while (rs.next()) {//sql�������� ���྿ �о�´�
				FileDTO dto = new FileDTO();//�ϳ��� ���������� ������ ��ü
				dto.setIdx(rs.getInt("idx"));
				dto.setRef(rs.getInt("ref"));
				dto.setLev(rs.getInt("lev"));
				dto.setSeq(rs.getInt("seq"));
				dto.setFileName(rs.getString("filename"));
				dto.setFolderName(rs.getString("folderName"));
				dto.setFileExtension(rs.getString("fileExtension"));
				dto.setFileSize(rs.getLong("filesize"));
				dto.setWdate(rs.getString("wdate"));
				list.add(dto);//ArrayList�� �������� ��ü �߰�
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps, rs);
		} // try - catch - finally
		return list;
	}

	public ArrayList<FileDTO> searchByFoldername(String keyword) {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT idx,ref,lev,seq,filename,folderName,fileExtension,filesize,wdate FROM fileboard where upper(folderName) like '%'||upper('"+keyword+"')||'%'  AND fileName IS NULL order by ref desc";
		
		try {
			con = UtillDB.getCon(con);//db�� �����Ѵ�
			ps = con.prepareStatement(sql);//sql�� �� PreparedStatement��ü�� ��´�.
			rs = ps.executeQuery();//sql�� ����
			while (rs.next()) {//sql�������� ���྿ �о�´�
				FileDTO dto = new FileDTO();//�ϳ��� ���������� ������ ��ü
				dto.setIdx(rs.getInt("idx"));
				dto.setRef(rs.getInt("ref"));
				dto.setLev(rs.getInt("lev"));
				dto.setSeq(rs.getInt("seq"));
				dto.setFileName(rs.getString("filename"));
				dto.setFolderName(rs.getString("folderName"));
				dto.setFileExtension(rs.getString("fileExtension"));
				dto.setFileSize(rs.getLong("filesize"));
				dto.setWdate(rs.getString("wdate"));
				list.add(dto);//ArrayList�� �������� ��ü �߰�
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps, rs);
		} // try - catch - finally
		return list;
	}

	public ArrayList<FileDTO> searchByFilenameAndFoldername(String keyword) {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT idx,ref,lev,seq,filename,folderName,fileExtension,filesize,wdate FROM fileboard where upper(filename) like '%'||upper('"+keyword+"')||'%' OR upper(folderName) like '%'||upper('"+keyword+"')||'%' order by ref desc";
		
		try {
			con = UtillDB.getCon(con);//db�� �����Ѵ�
			ps = con.prepareStatement(sql);//sql�� �� PreparedStatement��ü�� ��´�.
			rs = ps.executeQuery();//sql�� ����
			while (rs.next()) {//sql�������� ���྿ �о�´�
				FileDTO dto = new FileDTO();//�ϳ��� ���������� ������ ��ü
				dto.setIdx(rs.getInt("idx"));
				dto.setRef(rs.getInt("ref"));
				dto.setLev(rs.getInt("lev"));
				dto.setSeq(rs.getInt("seq"));
				dto.setFileName(rs.getString("filename"));
				dto.setFolderName(rs.getString("folderName"));
				dto.setFileExtension(rs.getString("fileExtension"));
				dto.setFileSize(rs.getLong("filesize"));
				dto.setWdate(rs.getString("wdate"));
				list.add(dto);//ArrayList�� �������� ��ü �߰�
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps, rs);
		} // try - catch - finally
		return list;
	}
	
	public ArrayList<FileDTO> sort(String sortType,String sortWay) {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM fileboard where seq=0 and lev=0 order by "+sortType+" "+sortWay;
		try {
			con = UtillDB.getCon(con);//db�� �����Ѵ�
			ps = con.prepareStatement(sql);//sql�� �� PreparedStatement��ü�� ��´�.
			rs = ps.executeQuery();//sql�� ����
			while (rs.next()) {//sql�������� ���྿ �о�´�
				FileDTO dto = new FileDTO();//�ϳ��� ���������� ������ ��ü
				dto.setIdx(rs.getInt("idx"));
				dto.setRef(rs.getInt("ref"));
				dto.setLev(rs.getInt("lev"));
				dto.setSeq(rs.getInt("seq"));
				dto.setFileName(rs.getString("filename"));
				dto.setFolderName(rs.getString("folderName"));
				dto.setFileExtension(rs.getString("fileExtension"));
				dto.setFileSize(rs.getLong("filesize"));
				dto.setWdate(rs.getString("wdate"));
				list.add(dto);//ArrayList�� �������� ��ü �߰�
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			UtillDB.closeDB(con, ps, rs);
		} // try - catch - finally
		return list;
	}
}
