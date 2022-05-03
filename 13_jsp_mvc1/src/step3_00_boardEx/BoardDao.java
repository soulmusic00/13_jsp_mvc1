package step3_00_boardEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDao {
	
	private BoardDao() {}
	private static BoardDao instance = new BoardDao();
	public static BoardDao getInstance() {
		return instance;
	}

	
	private Connection conn 		= null;
	private PreparedStatement pstmt = null;
	private ResultSet rs 			= null;

	
	public Connection getConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/STEP3_BOARD_EX?serverTimezone=UTC";
			String user = "root";
			String passwd = "1234";
			
			conn = DriverManager.getConnection(url, user, passwd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	
	}

	// 전체 게시글을 조회하는 DAO
	public ArrayList<BoardDto> getAllBoard() {

		ArrayList<BoardDto> boardList = new ArrayList<BoardDto>();
		BoardDto boardDto = null;
		
		try {
			
			conn  = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM BOARD");
			rs    = pstmt.executeQuery();
			
			while (rs.next()) {

				boardDto = new BoardDto();
				boardDto.setNum(rs.getInt("num")); 				//boardDto.setNum(rs.getInt(1));	     			
	 			boardDto.setWriter(rs.getString("writer")); 	//boardDto.setWriter(rs.getString(2));
	  			boardDto.setEmail(rs.getString("email")); 		//boardDto.setEmail(rs.getString(3));
				boardDto.setSubject(rs.getString("subject")); 	//boardDto.setSubject(rs.getString(4));
				boardDto.setPassword(rs.getString("password")); //boardDto.setPassword(rs.getString(5));
				boardDto.setRegDate(rs.getDate("reg_date")); 	//boardDto.setRegDate(rs.getDate(6)); 
				boardDto.setReadCount(rs.getInt("read_count")); //boardDto.setReadCount(rs.getInt(7));
				boardDto.setContent(rs.getString("content")); 	//boardDto.setContent(rs.getString(8));
				boardList.add(boardDto);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return boardList;
		
	}
	
	
	// 하나의 게시글을 조회하는 DAO
	public BoardDto getOneBoard(int num) {

		BoardDto boardDto = new BoardDto();

		try {
			
			conn = getConnection();

			pstmt = conn.prepareStatement("UPDATE BOARD SET READ_COUNT=READ_COUNT+1 WHERE NUM=?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				boardDto.setNum(rs.getInt("num"));
				boardDto.setWriter(rs.getString("writer"));
				boardDto.setEmail(rs.getString("email"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setPassword(rs.getString("password"));
				boardDto.setRegDate(rs.getDate("reg_date"));
				boardDto.setReadCount(rs.getInt("read_count"));
				boardDto.setContent(rs.getString("content"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return boardDto;
		
	}

	
	// 수정할 게시글을 조회하는 DAO
	public BoardDto getOneUpdateBoard(int num) {

		BoardDto boardDto = new BoardDto();

		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				boardDto.setNum(rs.getInt("num"));
				boardDto.setWriter(rs.getString("writer"));
				boardDto.setEmail(rs.getString("email"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setPassword(rs.getString("password"));
				boardDto.setRegDate(rs.getDate("reg_date"));
				boardDto.setReadCount(rs.getInt("read_count"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return boardDto;
		
	}

	
	// 비밀번호를 인증하는 DAO
	public boolean validMemberCheck(BoardDto boardDto) {

		boolean isValidMember = false;
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=? AND PASSWORD=?");
			pstmt.setInt(1, boardDto.getNum());
			pstmt.setString(2, boardDto.getPassword());
			rs = pstmt.executeQuery();

			if (rs.next()) 	isValidMember = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}

		return isValidMember;
		
	}
	
	
	// 게시글을 추가하는 DAO
	public void insertBoard(BoardDto boardDto) {

		try {
			
			conn = getConnection();
			String sql = "INSERT INTO BOARD(WRITER,EMAIL,SUBJECT,PASSWORD,REG_DATE,READ_COUNT,CONTENT)";
				   sql += "VALUES(?, ?, ?, ?, now(), 0, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardDto.getWriter());
			pstmt.setString(2, boardDto.getEmail());
			pstmt.setString(3, boardDto.getSubject());
			pstmt.setString(4, boardDto.getPassword());
			pstmt.setString(5, boardDto.getContent());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}	
		
	}
	
	
	// 게시글을 수정하는 DAO
	public boolean updateBoard(BoardDto boardDto) {

		boolean isUpdate = false;
		
		try {
			
			if (validMemberCheck(boardDto)) {
				conn = getConnection();
				pstmt = conn.prepareStatement("UPDATE BOARD SET SUBJECT=?, CONTENT=? WHERE NUM=?");
				pstmt.setString(1, boardDto.getSubject());
				pstmt.setString(2, boardDto.getContent());
				pstmt.setInt(3, boardDto.getNum());
				pstmt.executeUpdate();
				isUpdate = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return isUpdate;
		
	}

	
	// 게시글을 삭제하는 DAO
	public boolean deleteBoard(BoardDto boardDto) {

		boolean isDelete = false;
		
		try {
			
			if (validMemberCheck(boardDto)) {
				conn = getConnection();
				pstmt = conn.prepareStatement("DELETE FROM BOARD WHERE NUM=?");
				pstmt.setInt(1, boardDto.getNum());
				pstmt.executeUpdate();
				isDelete = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return isDelete;
		
	}
	
	
}
