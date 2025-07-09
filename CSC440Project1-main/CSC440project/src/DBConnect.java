import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DBConnect {

    // NEW DBCONNECT

    public static void main ( final String[] args ) {
        final String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/arjackmo";
        final String user = "arjackmo";
        final String password = "200409231";

        // Using try-with-resources for Connection
        try ( Connection conn = DriverManager.getConnection( url, user, password ) ) {
            System.out.println( "Successfully connected to the database." );

            DBConnect.dropTables( conn );
            // Step 1: Create Tables
            createStudentTable( conn );
            createFacultyDetailsTable( conn );
            createEnrollmentTable( conn );
            createCourseTable( conn );
            createTeachingAssistantTable( conn );
            createETextbookTable( conn );
            createChapterTable( conn );
            createSectionTable( conn );
            createBlockTable( conn );
            createActivityTable( conn );
            createQuestionTable( conn );
            createStudentCreditTable( conn );
            createStudentQuestionsTable( conn );

            // Step 2: Insert Data
            insertStudentData( conn, "S123", "John Doe", "password123", "john.doe@example.com" );
            insertCourseData( conn, "C789", "Introduction to Databases", "DBTextbook", "Lecture", "F456", null,
                    Date.valueOf( "2023-01-10" ), Date.valueOf( "2023-05-15" ), "TOKEN123", 100 );
            insertEnrollmentData( conn, "C789", "S123", "Enrolled" );
            insertTeachingAssistantData( conn, "Alice", "Johnson", "alice.johnson@example.com", "tapassword", "C789",
                    "F456" );
            insertETextbookData( conn, "1", "Database Systems" );
            // insertChapterData( conn, 1, "chap01", "Introduction", null );
            insertSectionData( conn, "1", "chap01", "sec01", "Basics", null );
            insertBlockData( conn, "1", "chap01", "sec01", "block01", "text", "Welcome to Databases", null );
            insertActivityData( conn, "1", "chap01", "sec01", "block01", "ACT001", null );
            insertQuestionData( conn, "1", "chap01", "sec01", "block01", "ACT001", "Q1", "What is a database?",
                    "A collection of data", null, "A type of food", null, "An operating system", null,
                    "A programming language", null, 1 );
            insertStudentCreditData( conn, "S123", "C789", 10, 1 );
            insertStudentQuestionsData( conn, "S123", "C789", 1, "chap01", "sec01", "block01", "ACT001", "Q1", 1,
                    new Timestamp( System.currentTimeMillis() ) );

            // Step 3: Query Data
            queryStudentData( conn, "S123" );
            queryFacultyDetails( conn, "F456" );
            queryCourseData( conn, "C789" );
            queryEnrollmentData( conn, "C789" );
            queryTeachingAssistantData( conn, "TA001" );
            queryETextbookData( conn, 1 );
            queryChapterData( conn, 1 );
            querySectionData( conn, 1, "chap01" );
            queryBlockData( conn, 1, "chap01", "sec01" );
            queryActivityData( conn, 1, "chap01", "sec01" );
            queryQuestionData( conn, 1, "chap01", "sec01", "block01", "ACT001" );
            queryStudentCreditData( conn, "S123", "C789" );
            queryStudentQuestionsData( conn, "S123", "C789" );

            // Step 4: Update Data
            updateStudentData( conn, "S123", "John Doe", "newpassword", "john.doe@example.com" );
            updateFacultyDetails( conn, "F456", "Jane", "Doe", "jane.doe@example.com", "newsecurepass" );
            updateCourseData( conn, "C789", "Intro to DB Systems", "AdvancedDBTextbook", "Lecture", "F456", "TA001",
                    Date.valueOf( "2023-01-10" ), Date.valueOf( "2023-05-15" ), "TOKEN123", 100 );
            updateEnrollmentStatus( conn, "C789", "S123", "Completed" );
            updateTeachingAssistantData( conn, "TA001", "Alice", "Smith", "alice.smith@example.com", "newtapassword",
                    "C789", "F456" );
            updateETextbookData( conn, 1, "Advanced Database Systems" );
            updateChapterData( conn, 1, "chap01", "Introduction to Databases", null );
            updateSectionData( conn, 1, "chap01", "sec01", "Database Basics", null );
            updateBlockData( conn, 1, "chap01", "sec01", "block01", "text", "Updated content", null );
            updateActivityData( conn, 1, "chap01", "sec01", "block01", "ACT001", "yes" );
            updateQuestionData( conn, 1, "chap01", "sec01", "block01", "ACT001", "Q1", "What is a database system?",
                    "A collection of data and DBMS", null, "A type of food", null, "An operating system", null,
                    "A programming language", null, 1 );
            updateStudentCreditData( conn, "S123", "C789", 20, 2 );
            updateStudentQuestionsData( conn, "S123", "C789", 1, "chap01", "sec01", "block01", "ACT001", "Q1", 1,
                    new Timestamp( System.currentTimeMillis() ) );

            // Step 5: Query Data After Update
            queryStudentData( conn, "S123" );
            queryFacultyDetails( conn, "F456" );
            queryCourseData( conn, "C789" );
            queryEnrollmentData( conn, "C789" );
            queryTeachingAssistantData( conn, "TA001" );
            queryETextbookData( conn, 1 );
            queryChapterData( conn, 1 );
            querySectionData( conn, 1, "chap01" );
            queryBlockData( conn, 1, "chap01", "sec01" );
            queryActivityData( conn, 1, "chap01", "sec01" );
            queryQuestionData( conn, 1, "chap01", "sec01", "block01", "ACT001" );
            queryStudentCreditData( conn, "S123", "C789" );
            queryStudentQuestionsData( conn, "S123", "C789" );

            // Step 6: Delete Data
            deleteStudentQuestionsData( conn, "S123", "C789", 1, "chap01", "sec01", "block01", "ACT001", "Q1" );
            deleteStudentCreditData( conn, "S123", "C789" );
            deleteQuestionData( conn, 1, "chap01", "sec01", "block01", "ACT001", "Q1" );
            deleteActivityData( conn, 1, "chap01", "sec01", "block01", "ACT001" );
            deleteBlockData( conn, 1, "chap01", "sec01", "block01" );
            deleteSectionData( conn, 1, "chap01", "sec01" );
            deleteChapterData( conn, 1, "chap01" );
            deleteETextbookData( conn, 1 );
            deleteTeachingAssistantData( conn, "TA001" );
            deleteEnrollmentData( conn, "C789", "S123" );
            deleteCourseData( conn, "C789" );
            deleteFacultyDetails( conn, "F456" );
            deleteStudentData( conn, "S123" );

            // Step 7: Query Data After Deletion
            queryStudentData( conn, "S123" );
            queryFacultyDetails( conn, "F456" );
            queryCourseData( conn, "C789" );
            queryEnrollmentData( conn, "C789" );
            queryTeachingAssistantData( conn, "TA001" );
            queryETextbookData( conn, 1 );
            queryChapterData( conn, 1 );
            querySectionData( conn, 1, "chap01" );
            queryBlockData( conn, 1, "chap01", "sec01" );
            queryActivityData( conn, 1, "chap01", "sec01" );
            queryQuestionData( conn, 1, "chap01", "sec01", "block01", "ACT001" );
            queryStudentCreditData( conn, "S123", "C789" );
            queryStudentQuestionsData( conn, "S123", "C789" );

        }
        catch ( final SQLException e ) {
            e.printStackTrace();
        }
    }

    public static void dropTables ( final Connection conn ) throws SQLException {
        final String[] tables = { "StudentQuestions", "StudentCredit", "Question", "Activity", "Block", "Section",
                "Chapter", "ETextbook", "TeachingAssistant", "Enrollment", "FacultyDetails", "Course", "Student" };

        try ( Statement stmt = conn.createStatement() ) {
            // Disable foreign key checks to prevent issues with dependent
            // tables
            stmt.execute( "SET FOREIGN_KEY_CHECKS = 0" );

            for ( final String table : tables ) {
                stmt.executeUpdate( "DROP TABLE IF EXISTS " + table );
                System.out.println( "Table '" + table + "' dropped." );
            }

            // Re-enable foreign key checks
            stmt.execute( "SET FOREIGN_KEY_CHECKS = 1" );
        }
        catch ( final SQLException e ) {
            System.err.println( "Error dropping tables: " + e.getMessage() );
            throw e;
        }
    }

    // Method to create the FacultyDetails table
    public static void createFacultyDetailsTable ( final Connection conn ) throws SQLException {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS FacultyDetails ("
                + "faculty_id VARCHAR(255) PRIMARY KEY, " + "FirstName VARCHAR(100), " + "LastName VARCHAR(100), "
                + "Email VARCHAR(255), " + "Password VARCHAR(255) " + ");";
        try ( PreparedStatement pstmt = conn.prepareStatement( createTableSQL ) ) {
            pstmt.executeUpdate();
            System.out.println( "Table 'FacultyDetails' created or already exists." );
        }
    }

    public static void insertFacultyDetails ( final Connection conn, final String firstName, final String lastName,
            final String email, final String password ) throws SQLException {
        // Generate the unique faculty_id
        final LocalDate currentDate = LocalDate.now();
        final String monthYear = currentDate.format( DateTimeFormatter.ofPattern( "MMyy" ) );
        final String facultyId = ( firstName.substring( 0, 2 ) + lastName.substring( 0, 2 ) + monthYear );

        final String insertSQL = "INSERT INTO FacultyDetails (faculty_id, FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setString( 1, facultyId );
            pstmt.setString( 2, firstName );
            pstmt.setString( 3, lastName );
            pstmt.setString( 4, email );
            pstmt.setString( 5, password );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'FacultyDetails'." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) { // Duplicate entry
                System.out.println( "Record already exists in 'FacultyDetails'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateFacultyDetails ( final Connection conn, final String facultyId, final String firstName,
            final String lastName, final String email, final String password ) throws SQLException {
        final String updateSQL = "UPDATE FacultyDetails SET FirstName = ?, LastName = ?, Email = ?, Password = ? WHERE faculty_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, firstName );
            pstmt.setString( 2, lastName );
            pstmt.setString( 3, email );
            pstmt.setString( 4, password );
            pstmt.setString( 5, facultyId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'FacultyDetails'." );
        }
    }

    public static void queryFacultyDetails ( final Connection conn, final String facultyId ) throws SQLException {
        final String querySQL = "SELECT * FROM FacultyDetails WHERE faculty_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, facultyId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    final String firstName = rs.getString( "FirstName" );
                    final String lastName = rs.getString( "LastName" );
                    final String email = rs.getString( "Email" );
                    final String password = rs.getString( "Password" );
                    System.out.println( "Faculty ID: " + facultyId + ", Name: " + firstName + " " + lastName
                            + ", Email: " + email );
                }
                else {
                    System.out.println( "No record found in 'FacultyDetails' for faculty_id: " + facultyId );
                }
            }
        }
    }

    public static void deleteFacultyDetails ( final Connection conn, final String facultyId ) throws SQLException {
        final String deleteSQL = "DELETE FROM FacultyDetails WHERE faculty_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setString( 1, facultyId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'FacultyDetails'." );
        }
    }

    public static void createEnrollmentTable ( final Connection conn ) throws SQLException {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS Enrollment (" + "course_id VARCHAR(100), "
                + "student_id VARCHAR(8), "
                + "Status ENUM('Enrolled', 'Completed', 'Dropped', 'Pending') DEFAULT 'Enrolled', "
                + "PRIMARY KEY (course_id, student_id)" + ");";
        try ( PreparedStatement pstmt = conn.prepareStatement( createTableSQL ) ) {
            pstmt.executeUpdate();
            System.out.println( "Table 'Enrollment' created or already exists." );
        }
    }

    public static void insertEnrollmentData ( final Connection conn, final String courseId, final String studentId,
            final String status ) throws SQLException {
        final String insertSQL = "INSERT INTO Enrollment (course_id, student_id, Status) VALUES (?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setString( 1, courseId );
            pstmt.setString( 2, studentId );
            pstmt.setString( 3, status );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Enrollment'." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) { // Duplicate entry
                System.out.println( "Record already exists in 'Enrollment'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateEnrollmentStatus ( final Connection conn, final String courseId, final String studentId,
            final String newStatus ) throws SQLException {
        final String updateSQL = "UPDATE Enrollment SET Status = ? WHERE course_id = ? AND student_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, newStatus );
            pstmt.setString( 2, courseId );
            pstmt.setString( 3, studentId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'Enrollment'." );
        }
    }

    public static void queryEnrollmentData ( final Connection conn, final String courseId ) throws SQLException {
        final String querySQL = "SELECT * FROM Enrollment WHERE course_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, courseId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println( "Enrollment Data for Course ID: " + courseId );
                while ( rs.next() ) {
                    final String studentId = rs.getString( "student_id" );
                    final String status = rs.getString( "Status" );
                    System.out.println( "Student ID: " + studentId + ", Status: " + status );
                }
            }
        }
    }

    public static void deleteEnrollmentData ( final Connection conn, final String courseId, final String studentId )
            throws SQLException {
        final String deleteSQL = "DELETE FROM Enrollment WHERE course_id = ? AND student_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setString( 1, courseId );
            pstmt.setString( 2, studentId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Enrollment'." );
        }
    }

    public static void createStudentTable ( final Connection conn ) {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS Student ("
                + "student_id VARCHAR(50) NOT NULL PRIMARY KEY, " + "full_name VARCHAR(100) NOT NULL, "
                + "password VARCHAR(255) NOT NULL, " + "email VARCHAR(100) NOT NULL UNIQUE" + ")";

        try ( Statement stmt = conn.createStatement() ) {
            stmt.execute( createTableSQL );
            System.out.println( "Student table created successfully." );
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void insertStudentData ( final Connection conn, final String studentId, final String fullName,
            final String password, final String email ) throws SQLException {
        final String insertSQL = "INSERT INTO Student (student_id, full_name, password, email) VALUES (?, ?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setString( 1, studentId );
            pstmt.setString( 2, fullName );
            pstmt.setString( 3, password );
            pstmt.setString( 4, email );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Student'." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'Student'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateStudentData ( final Connection conn, final String studentId, final String fullName,
            final String password, final String email ) throws SQLException {
        final String updateSQL = "UPDATE Student SET full_name = ?, password = ?, email = ? WHERE student_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, fullName );
            pstmt.setString( 2, password );
            pstmt.setString( 3, email );
            pstmt.setString( 4, studentId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'Student'." );
        }
    }

    public static void queryStudentData ( final Connection conn, final String studentId ) throws SQLException {
        final String querySQL = "SELECT * FROM Student WHERE student_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, studentId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    final String fullName = rs.getString( "full_name" );
                    final String email = rs.getString( "email" );
                    System.out.println( "Student ID: " + studentId + ", Name: " + fullName + ", Email: " + email );
                }
                else {
                    System.out.println( "No record found in 'Student' for student_id: " + studentId );
                }
            }
        }
    }

    public static void deleteStudentData ( final Connection conn, final String studentId ) throws SQLException {
        final String deleteSQL = "DELETE FROM Student WHERE student_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setString( 1, studentId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Student'." );
        }
    }

    public static void createStudentCreditTable ( final Connection conn ) {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS StudentCredit (" + "student_id VARCHAR(50) NOT NULL, "
                + "registered_course_id VARCHAR(50) NOT NULL, " + "total_participation_points INT NOT NULL, "
                + "num_of_finished_activities INT NOT NULL, " + "PRIMARY KEY (student_id, registered_course_id), "
                + "FOREIGN KEY (student_id) REFERENCES Student(student_id)" + ")";

        try ( Statement stmt = conn.createStatement() ) {
            stmt.execute( createTableSQL );
            System.out.println( "StudentCredit table created successfully." );
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void insertStudentCreditData ( final Connection conn, final String studentId,
            final String registeredCourseId, final int totalParticipationPoints, final int numOfFinishedActivities )
            throws SQLException {
        final String insertSQL = "INSERT INTO StudentCredit (student_id, registered_course_id, total_participation_points, num_of_finished_activities) VALUES (?, ?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setString( 1, studentId );
            pstmt.setString( 2, registeredCourseId );
            pstmt.setInt( 3, totalParticipationPoints );
            pstmt.setInt( 4, numOfFinishedActivities );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'StudentCredit'." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'StudentCredit'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateStudentCreditData ( final Connection conn, final String studentId,
            final String registeredCourseId, final int totalParticipationPoints, final int numOfFinishedActivities )
            throws SQLException {
        final String updateSQL = "UPDATE StudentCredit SET total_participation_points = ?, num_of_finished_activities = ? WHERE student_id = ? AND registered_course_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setInt( 1, totalParticipationPoints );
            pstmt.setInt( 2, numOfFinishedActivities );
            pstmt.setString( 3, studentId );
            pstmt.setString( 4, registeredCourseId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'StudentCredit'." );
        }
    }

    public static void queryStudentCreditData ( final Connection conn, final String studentId,
            final String registeredCourseId ) throws SQLException {
        final String querySQL = "SELECT * FROM StudentCredit WHERE student_id = ? AND registered_course_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, studentId );
            pstmt.setString( 2, registeredCourseId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    final int totalPoints = rs.getInt( "total_participation_points" );
                    final int finishedActivities = rs.getInt( "num_of_finished_activities" );
                    System.out.println( "Student ID: " + studentId + ", Course ID: " + registeredCourseId
                            + ", Total Points: " + totalPoints + ", Finished Activities: " + finishedActivities );
                }
                else {
                    System.out.println( "No record found in 'StudentCredit' for student_id: " + studentId
                            + " and registered_course_id: " + registeredCourseId );
                }
            }
        }
    }

    public static void deleteStudentCreditData ( final Connection conn, final String studentId,
            final String registeredCourseId ) throws SQLException {
        final String deleteSQL = "DELETE FROM StudentCredit WHERE student_id = ? AND registered_course_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setString( 1, studentId );
            pstmt.setString( 2, registeredCourseId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'StudentCredit'." );
        }
    }

    public static void createStudentQuestionsTable ( final Connection conn ) {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS StudentQuestions ("
                + "student_id VARCHAR(50) NOT NULL, " + "course_id VARCHAR(50) NOT NULL, "
                + "textbook_id INT NOT NULL, " + "chapter_id VARCHAR(50) NOT NULL, "
                + "section_id VARCHAR(50) NOT NULL, " + "block_id VARCHAR(50) NOT NULL, "
                + "unique_activity_id VARCHAR(50) NOT NULL, " + "question_id VARCHAR(50) NOT NULL, "
                + "point INT NOT NULL, " + "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                + "PRIMARY KEY (student_id, course_id, textbook_id, chapter_id, section_id, block_id, unique_activity_id, question_id), "
                + "FOREIGN KEY (student_id) REFERENCES Student(student_id)" + ")";

        try ( Statement stmt = conn.createStatement() ) {
            stmt.execute( createTableSQL );
            System.out.println( "StudentQuestions table created successfully." );
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void insertStudentQuestionsData ( final Connection conn, final String studentId,
            final String courseId, final int textbookId, final String chapterId, final String sectionId,
            final String blockId, final String uniqueActivityId, final String questionId, final int point,
            final Timestamp timestamp ) throws SQLException {
        final String insertSQL = "INSERT INTO StudentQuestions (student_id, course_id, textbook_id, chapter_id, section_id, block_id, unique_activity_id, question_id, point, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setString( 1, studentId );
            pstmt.setString( 2, courseId );
            pstmt.setInt( 3, textbookId );
            pstmt.setString( 4, chapterId );
            pstmt.setString( 5, sectionId );
            pstmt.setString( 6, blockId );
            pstmt.setString( 7, uniqueActivityId );
            pstmt.setString( 8, questionId );
            pstmt.setInt( 9, point );
            pstmt.setTimestamp( 10, timestamp );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'StudentQuestions'." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'StudentQuestions'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateStudentQuestionsData ( final Connection conn, final String studentId,
            final String courseId, final int textbookId, final String chapterId, final String sectionId,
            final String blockId, final String uniqueActivityId, final String questionId, final int point,
            final Timestamp timestamp ) throws SQLException {
        final String updateSQL = "UPDATE StudentQuestions SET point = ?, timestamp = ? WHERE student_id = ? AND course_id = ? AND textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ? AND question_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setInt( 1, point );
            pstmt.setTimestamp( 2, timestamp );
            pstmt.setString( 3, studentId );
            pstmt.setString( 4, courseId );
            pstmt.setInt( 5, textbookId );
            pstmt.setString( 6, chapterId );
            pstmt.setString( 7, sectionId );
            pstmt.setString( 8, blockId );
            pstmt.setString( 9, uniqueActivityId );
            pstmt.setString( 10, questionId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'StudentQuestions'." );
        }
    }

    public static void queryStudentQuestionsData ( final Connection conn, final String studentId,
            final String courseId ) throws SQLException {
        final String querySQL = "SELECT * FROM StudentQuestions WHERE student_id = ? AND course_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, studentId );
            pstmt.setString( 2, courseId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println( "StudentQuestions Data for Student ID: " + studentId + ", Course ID: " + courseId );
                while ( rs.next() ) {
                    final int textbookId = rs.getInt( "textbook_id" );
                    final String chapterId = rs.getString( "chapter_id" );
                    final String sectionId = rs.getString( "section_id" );
                    final String blockId = rs.getString( "block_id" );
                    final String uniqueActivityId = rs.getString( "unique_activity_id" );
                    final String questionId = rs.getString( "question_id" );
                    final int point = rs.getInt( "point" );
                    final Timestamp timestamp = rs.getTimestamp( "timestamp" );
                    System.out.println( "Textbook ID: " + textbookId + ", Chapter ID: " + chapterId + ", Section ID: "
                            + sectionId + ", Block ID: " + blockId + ", Activity ID: " + uniqueActivityId
                            + ", Question ID: " + questionId + ", Point: " + point + ", Timestamp: " + timestamp );
                }
            }
        }
    }

    public static void deleteStudentQuestionsData ( final Connection conn, final String studentId,
            final String courseId, final int textbookId, final String chapterId, final String sectionId,
            final String blockId, final String uniqueActivityId, final String questionId ) throws SQLException {
        final String deleteSQL = "DELETE FROM StudentQuestions WHERE student_id = ? AND course_id = ? AND textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ? AND question_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setString( 1, studentId );
            pstmt.setString( 2, courseId );
            pstmt.setInt( 3, textbookId );
            pstmt.setString( 4, chapterId );
            pstmt.setString( 5, sectionId );
            pstmt.setString( 6, blockId );
            pstmt.setString( 7, uniqueActivityId );
            pstmt.setString( 8, questionId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'StudentQuestions'." );
        }
    }

    public static void createTeachingAssistantTable ( final Connection conn ) throws SQLException {
        final String createTableSQL = """
                CREATE TABLE IF NOT EXISTS TeachingAssistant (
                    TA_id VARCHAR(50) NOT NULL PRIMARY KEY,
                    first_name VARCHAR(50) NOT NULL,
                    last_name VARCHAR(50) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    course_id VARCHAR(50) NOT NULL,
                    faculty_id VARCHAR(50) NOT NULL,
                    FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY (faculty_id) REFERENCES FacultyDetails(faculty_id) ON DELETE CASCADE ON UPDATE CASCADE
                );
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( createTableSQL ) ) {
            pstmt.executeUpdate();
            System.out.println( "Table 'TeachingAssistant' created successfully." );
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void insertTeachingAssistantData ( final Connection conn, final String firstName,
            final String lastName, final String email, final String password, final String courseId,
            final String facultyId ) throws SQLException {
        final String insertSQL = "INSERT INTO TeachingAssistant (first_name, last_name, email, password, course_id, faculty_id) VALUES (?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL, Statement.RETURN_GENERATED_KEYS ) ) {
            pstmt.setString( 1, firstName );
            pstmt.setString( 2, lastName );
            pstmt.setString( 3, email );
            pstmt.setString( 4, password );
            pstmt.setString( 5, courseId );
            pstmt.setString( 6, facultyId );

            final int rowsAffected = pstmt.executeUpdate();

            try ( ResultSet rs = pstmt.getGeneratedKeys() ) {
                if ( rs.next() ) {
                    final int generatedTaId = rs.getInt( 1 ); // Retrieve the
                                                              // generated TA_id
                    System.out.println( "Inserted TA with ID: " + generatedTaId );
                }
            }

            System.out.println( rowsAffected + " row(s) inserted into 'TeachingAssistant'." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'TeachingAssistant'. Skipping insertion." );
            }
            else if ( e.getErrorCode() == 1452 ) { // Foreign key constraint
                                                   // fails
                System.out.println( "Cannot insert into 'TeachingAssistant' due to foreign key constraint violation." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateTeachingAssistantData ( final Connection conn, final String taId, final String firstName,
            final String lastName, final String email, final String password, final String courseId,
            final String facultyId ) throws SQLException {
        final String updateSQL = "UPDATE TeachingAssistant SET first_name = ?, last_name = ?, email = ?, password = ?, course_id = ?, faculty_id = ? WHERE TA_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, firstName );
            pstmt.setString( 2, lastName );
            pstmt.setString( 3, email );
            pstmt.setString( 4, password );
            pstmt.setString( 5, courseId );
            pstmt.setString( 6, facultyId );
            pstmt.setString( 7, taId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'TeachingAssistant'." );
        }
    }

    public static void queryTeachingAssistantData ( final Connection conn, final String taId ) throws SQLException {
        final String querySQL = "SELECT * FROM TeachingAssistant WHERE TA_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, taId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    final String firstName = rs.getString( "first_name" );
                    final String lastName = rs.getString( "last_name" );
                    final String email = rs.getString( "email" );
                    final String courseId = rs.getString( "course_id" );
                    final String facultyId = rs.getString( "faculty_id" );
                    System.out.println( "TA ID: " + taId + ", Name: " + firstName + " " + lastName + ", Email: " + email
                            + ", Course ID: " + courseId + ", Faculty ID: " + facultyId );
                }
                else {
                    System.out.println( "No record found in 'TeachingAssistant' for TA_id: " + taId );
                }
            }
        }
    }

    public static void deleteTeachingAssistantData ( final Connection conn, final String taId ) throws SQLException {
        final String deleteSQL = "DELETE FROM TeachingAssistant WHERE TA_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setString( 1, taId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'TeachingAssistant'." );
        }
    }

    // Method to create the Admins table
    private static void createAdminsTable ( final Connection conn ) throws SQLException {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS Admins (" + "UserID VARCHAR(8) PRIMARY KEY, "
                + "FOREIGN KEY (UserID) REFERENCES User(UserID) ON DELETE CASCADE" + ");";
        try ( PreparedStatement pstmt = conn.prepareStatement( createTableSQL ) ) {
            pstmt.executeUpdate();
            System.out.println( "Table 'Admins' created or already exists." );
        }
    }

    // Method to insert data into Admins
    public static void insertAdminData ( final Connection conn, final String userID ) throws SQLException {
        final String insertSQL = "INSERT INTO Admins (UserID) VALUES (?);";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setString( 1, userID );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Admins'." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1452 ) { // Foreign key constraint fails
                System.out.println( "Cannot insert into 'Admins' because the UserID does not exist in 'User' table." );
            }
            else if ( e.getErrorCode() == 1062 ) { // Duplicate entry
                System.out.println( "Record already exists in 'Admins'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    // Update Admins Data
    public static void updateAdminDetails ( final Connection conn, final String userID ) throws SQLException {
        final String updateSQL = "UPDATE Admins SET UserID = ? WHERE UserID = ?;";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, userID );
            pstmt.setString( 2, userID );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'Admins'." );
        }
    }

    // Query Admins Data
    public static void queryAdminDetails ( final Connection conn, final String userID ) throws SQLException {
        final String querySQL = "SELECT * FROM Admins WHERE UserID = ?;";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, userID );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    System.out.println( "Admins - UserID: " + userID );
                }
                else {
                    System.out.println( "No record found in 'Admins' for UserID: " + userID );
                }
            }
        }
    }

    // Delete Admins Data
    public static void deleteAdminDetails ( final Connection conn, final String userID ) throws SQLException {
        final String deleteSQL = "DELETE FROM Admins WHERE UserID = ?;";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setString( 1, userID );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Admins'." );
        }
    }

    // Method to create the Notification table
    private static void createNotificationTable ( final Connection conn ) throws SQLException {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS Notification ("
                + "NotificationID INT AUTO_INCREMENT PRIMARY KEY, " + "UserID VARCHAR(8), " + "Message VARCHAR(255), "
                + "FOREIGN KEY (UserID) REFERENCES User(UserID) ON DELETE CASCADE" + ");";
        try ( PreparedStatement pstmt = conn.prepareStatement( createTableSQL ) ) {
            pstmt.executeUpdate();
            System.out.println( "Table 'Notification' created or already exists." );
        }
    }

    // Method to insert notification data
    public static void insertNotificationData ( final Connection conn, final int notificationID, final String userID,
            final String message ) throws SQLException {
        final String insertSQL = "INSERT INTO Notification (NotificationID, UserID, Message) " + "VALUES (?, ?, ?);";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setInt( 1, notificationID );
            pstmt.setString( 2, userID );
            pstmt.setString( 3, message );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Notification'." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Notification record already exists. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    // Method to query notification data
    public static void queryNotificationData ( final Connection conn ) throws SQLException {
        final String querySQL = "SELECT * FROM Notification;";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ); ResultSet rs = pstmt.executeQuery() ) {

            System.out.println( "Notification Data:" );
            while ( rs.next() ) {
                final int notificationID = rs.getInt( "NotificationID" );
                final String userID = rs.getString( "UserID" );
                final String message = rs.getString( "Message" );

                System.out.println(
                        "NotificationID: " + notificationID + ", UserID: " + userID + ", Message: " + message );
            }
        }
    }

    // Method to delete notification data
    public static void deleteNotificationData ( final Connection conn, final int notificationID ) throws SQLException {
        final String deleteSQL = "DELETE FROM Notification WHERE NotificationID = ?;";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, notificationID );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Notification'." );
        }
    }

    public static void createCourseTable ( final Connection conn ) throws SQLException {
        final String createTableSQL = """
                CREATE TABLE IF NOT EXISTS Course (
                    course_id VARCHAR(255) PRIMARY KEY,
                    CourseName VARCHAR(255),
                    ETextbook VARCHAR(255),
                    CourseType VARCHAR(255),
                    faculty_id VARCHAR(255),
                    TAID VARCHAR(255),
                    StartDate DATE,
                    EndDate DATE,
                    Token VARCHAR(255),
                    CourseCapacity INT,
                    FOREIGN KEY (faculty_id) REFERENCES FacultyDetails(faculty_id) ON DELETE CASCADE ON UPDATE CASCADE
                );
                """;
        try ( PreparedStatement pstmt = conn.prepareStatement( createTableSQL ) ) {
            pstmt.executeUpdate();
            System.out.println( "Table 'Course' created or already exists." );
        }
    }

    public static void insertCourseData ( final Connection conn, final String courseId, final String courseName,
            final String eTextbook, final String courseType, final String facultyId, final String taId,
            final Date startDate, final Date endDate, final String token, final int courseCapacity )
            throws SQLException {
        final String insertSQL = "INSERT INTO Course (course_id, CourseName, ETextbook, CourseType, faculty_id, TAID, StartDate, EndDate, Token, CourseCapacity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setString( 1, courseId );
            pstmt.setString( 2, courseName );
            pstmt.setString( 3, eTextbook );
            pstmt.setString( 4, courseType );
            pstmt.setString( 5, facultyId );

            // Set TAID or NULL if not provided
            if ( taId == null || taId.trim().isEmpty() ) {
                pstmt.setNull( 6, java.sql.Types.VARCHAR );
            }
            else {
                pstmt.setString( 6, taId );
            }

            pstmt.setDate( 7, startDate );
            pstmt.setDate( 8, endDate );
            pstmt.setString( 9, token );
            pstmt.setInt( 10, courseCapacity );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Course'." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'Course'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateCourseData ( final Connection conn, final String courseId, final String courseName,
            final String eTextbook, final String courseType, final String facultyId, final String taId,
            final Date startDate, final Date endDate, final String token, final int courseCapacity )
            throws SQLException {
        final String updateSQL = "UPDATE Course SET CourseName = ?, ETextbook = ?, CourseType = ?, faculty_id = ?, TAID = ?, StartDate = ?, EndDate = ?, Token = ?, CourseCapacity = ? WHERE course_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, courseName );
            pstmt.setString( 2, eTextbook );
            pstmt.setString( 3, courseType );
            pstmt.setString( 4, facultyId );
            pstmt.setString( 5, taId );
            pstmt.setDate( 6, startDate );
            pstmt.setDate( 7, endDate );
            pstmt.setString( 8, token );
            pstmt.setInt( 9, courseCapacity );
            pstmt.setString( 10, courseId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'Course'." );
        }
    }

    public static void queryCourseData ( final Connection conn, final String courseId ) throws SQLException {
        final String querySQL = "SELECT * FROM Course WHERE course_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, courseId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    final String courseName = rs.getString( "CourseName" );
                    final String eTextbook = rs.getString( "ETextbook" );
                    final String courseType = rs.getString( "CourseType" );
                    final String facultyId = rs.getString( "faculty_id" );
                    final String taId = rs.getString( "TAID" );
                    final Date startDate = rs.getDate( "StartDate" );
                    final Date endDate = rs.getDate( "EndDate" );
                    final String token = rs.getString( "Token" );
                    final int courseCapacity = rs.getInt( "CourseCapacity" );
                    System.out.println( "Course ID: " + courseId + ", Name: " + courseName + ", ETextbook: " + eTextbook
                            + ", Course Type: " + courseType + ", Faculty ID: " + facultyId + ", TA ID: " + taId
                            + ", Start Date: " + startDate + ", End Date: " + endDate + ", Token: " + token
                            + ", Capacity: " + courseCapacity );
                }
                else {
                    System.out.println( "No record found in 'Course' for course_id: " + courseId );
                }
            }
        }
    }

    public static void displayAllCourses ( final Connection conn ) {
        final String querySQL = "SELECT * FROM Course";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println( "\nCurrent Courses:" );
                if ( !rs.isBeforeFirst() ) {
                    System.out.println( "No courses available." );
                    return;
                }
                while ( rs.next() ) {
                    final String courseId = rs.getString( "course_id" );
                    final String courseName = rs.getString( "CourseName" );
                    final String eTextbook = rs.getString( "ETextbook" );
                    final String courseType = rs.getString( "CourseType" );
                    final String facultyId = rs.getString( "faculty_id" );
                    final String taId = rs.getString( "TAID" );
                    final Date startDate = rs.getDate( "StartDate" );
                    final Date endDate = rs.getDate( "EndDate" );
                    final String token = rs.getString( "Token" );
                    final int courseCapacity = rs.getInt( "CourseCapacity" );
                    System.out.println( "Course ID: " + courseId + ", Name: " + courseName + ", ETextbook: " + eTextbook
                            + ", Course Type: " + courseType + ", Faculty ID: " + facultyId + ", TA ID: " + taId
                            + ", Start Date: " + startDate + ", End Date: " + endDate + ", Token: " + token
                            + ", Capacity: " + courseCapacity );
                }
            }
        }
        catch ( final SQLException e ) {
            System.out.println( "Error while retrieving courses: " + e.getMessage() );
        }
    }

    public static void deleteCourseData ( final Connection conn, final String courseId ) throws SQLException {
        final String deleteSQL = "DELETE FROM Course WHERE course_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setString( 1, courseId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Course'." );
        }
    }

    public static void createETextbookTable ( final Connection conn ) throws SQLException {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS ETextbook (" + "TextbookID INT PRIMARY KEY, "
                + "Title VARCHAR(255) NOT NULL" + ");";
        try ( PreparedStatement pstmt = conn.prepareStatement( createTableSQL ) ) {
            pstmt.executeUpdate();
            System.out.println( "Table 'ETextbook' created or already exists." );
        }
    }

    public static void insertETextbookData ( final Connection conn, final String eTextbookID, final String title )
            throws SQLException {
        final String insertSQL = "INSERT INTO ETextbook (TextbookID, Title) VALUES (?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            // Convert the eTextbookID from String to int
            final int textbookID = Integer.parseInt( eTextbookID );

            // Set the parameters in the PreparedStatement
            pstmt.setInt( 1, textbookID );
            pstmt.setString( 2, title );

            // Execute the update and provide feedback
            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'ETextbook'." );
        }
        catch ( final NumberFormatException e ) {
            System.out.println( "Invalid eTextbookID: must be a numeric value." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'ETextbook'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateETextbookData ( final Connection conn, final int textbookId, final String title )
            throws SQLException {
        final String updateSQL = "UPDATE ETextbook SET Title = ? WHERE TextbookID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, title );
            pstmt.setInt( 2, textbookId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'ETextbook'." );
        }
    }

    public static void queryETextbookData ( final Connection conn, final int textbookId ) throws SQLException {
        final String querySQL = "SELECT * FROM ETextbook WHERE TextbookID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setInt( 1, textbookId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    final String title = rs.getString( "Title" );
                    System.out.println( "Textbook ID: " + textbookId + ", Title: " + title );
                }
                else {
                    System.out.println( "No record found in 'ETextbook' for TextbookID: " + textbookId );
                }
            }
        }
    }

    public static void deleteETextbookData ( final Connection conn, final int textbookId ) throws SQLException {
        final String deleteSQL = "DELETE FROM ETextbook WHERE TextbookID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, textbookId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'ETextbook'." );
        }
    }

    public static void createChapterTable ( final Connection conn ) throws SQLException {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS Chapter (" + "TextbookID INT, "
                + "ChapterID VARCHAR(255), " + "Title VARCHAR(255), " + "Hidden VARCHAR(255) DEFAULT 'no', "
                + "UNIQUE(TextbookID, ChapterID), " + "UNIQUE(TextbookID, Title), "
                + "PRIMARY KEY(TextbookID, ChapterID), "
                + "FOREIGN KEY (TextbookID) REFERENCES ETextbook(TextbookID) ON DELETE CASCADE" + ");";
        try ( PreparedStatement pstmt = conn.prepareStatement( createTableSQL ) ) {
            pstmt.executeUpdate();
            System.out.println( "Table 'Chapter' created or already exists." );
        }
    }

    public static void insertChapterData ( final Connection conn, final String textbookId, final String chapterId,
            final String title, final String hidden ) throws SQLException {
        final String insertSQLWithHidden = "INSERT INTO Chapter (TextbookID, ChapterID, Title, Hidden) VALUES (?, ?, ?, ?)";
        final String insertSQLWithoutHidden = "INSERT INTO Chapter (TextbookID, ChapterID, Title) VALUES (?, ?, ?)";

        try (
                PreparedStatement pstmt = conn
                        .prepareStatement( hidden == null ? insertSQLWithoutHidden : insertSQLWithHidden ) ) {
            // Convert textbookId from String to int
            final int textbookIdInt = Integer.parseInt( textbookId );

            pstmt.setInt( 1, textbookIdInt );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, title );

            // Only set the hidden column if a value is provided
            if ( hidden != null ) {
                pstmt.setString( 4, hidden );
            }

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Chapter'." );
        }
        catch ( final NumberFormatException e ) {
            System.out.println( "Invalid textbook ID: must be a numeric value." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'Chapter'. Skipping insertion." );
            }
            else if ( e.getErrorCode() == 1452 ) {
                System.out.println( "Cannot insert into 'Chapter' due to foreign key constraint violation." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateChapterData ( final Connection conn, final int textbookId, final String chapterId,
            final String title, final String hidden ) throws SQLException {
        final String updateSQL = "UPDATE Chapter SET Title = ?, Hidden = ? WHERE TextbookID = ? AND ChapterID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, title );
            pstmt.setString( 2, hidden );
            pstmt.setInt( 3, textbookId );
            pstmt.setString( 4, chapterId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'Chapter'." );
        }
    }

    public static void queryChapterData ( final Connection conn, final int textbookId ) throws SQLException {
        final String querySQL = "SELECT * FROM Chapter WHERE TextbookID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setInt( 1, textbookId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println( "Chapter Data for Textbook ID: " + textbookId );
                while ( rs.next() ) {
                    final String chapterId = rs.getString( "ChapterID" );
                    final String title = rs.getString( "Title" );
                    final String hidden = rs.getString( "Hidden" );
                    System.out.println( "Chapter ID: " + chapterId + ", Title: " + title + ", Hidden: " + hidden );
                }
            }
        }
    }

    public static void deleteChapterData ( final Connection conn, final int textbookId, final String chapterId )
            throws SQLException {
        final String deleteSQL = "DELETE FROM Chapter WHERE TextbookID = ? AND ChapterID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, textbookId );
            pstmt.setString( 2, chapterId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Chapter'." );
        }
    }

    public static void createSectionTable ( final Connection conn ) throws SQLException {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS Section (" + "TextbookID INT, "
                + "ChapterID VARCHAR(255), " + "SectionID VARCHAR(255), " + "Title VARCHAR(255), "
                + "Hidden VARCHAR(255) DEFAULT NULL, " // Allow NULL as default
                + "PRIMARY KEY(TextbookID, ChapterID, SectionID), "
                + "FOREIGN KEY (TextbookID, ChapterID) REFERENCES Chapter(TextbookID, ChapterID) ON DELETE CASCADE"
                + ");";
        try ( PreparedStatement pstmt = conn.prepareStatement( createTableSQL ) ) {
            pstmt.executeUpdate();
            System.out.println( "Table 'Section' created or already exists." );
        }
    }

    public static void insertSectionData ( final Connection conn, final String textbookId, final String chapterId,
            final String sectionId, final String title, final String hidden ) throws SQLException {
        final String insertSQL = "INSERT INTO Section (TextbookID, ChapterID, SectionID, Title, Hidden) VALUES (?, ?, ?, ?, ?)";

        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            // Convert textbookId from String to int
            final int textbookIdInt = Integer.parseInt( textbookId );

            pstmt.setInt( 1, textbookIdInt );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            pstmt.setString( 4, title );

            // Set hidden to NULL if it’s null, otherwise set its value
            if ( hidden == null ) {
                pstmt.setNull( 5, java.sql.Types.VARCHAR );
            }
            else {
                pstmt.setString( 5, hidden );
            }

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Section'." );
        }
        catch ( final NumberFormatException e ) {
            System.out.println( "Invalid textbook ID: must be a numeric value." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'Section'. Skipping insertion." );
            }
            else if ( e.getErrorCode() == 1452 ) {
                System.out.println( "Cannot insert into 'Section' due to foreign key constraint violation." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateSectionData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId, final String title, final String hidden ) throws SQLException {
        final String updateSQL = "UPDATE Section SET Title = ?, Hidden = ? WHERE TextbookID = ? AND ChapterID = ? AND SectionID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, title );
            pstmt.setString( 2, hidden );
            pstmt.setInt( 3, textbookId );
            pstmt.setString( 4, chapterId );
            pstmt.setString( 5, sectionId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'Section'." );
        }
    }

    public static void querySectionData ( final Connection conn, final int textbookId, final String chapterId )
            throws SQLException {
        final String querySQL = "SELECT * FROM Section WHERE TextbookID = ? AND ChapterID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setInt( 1, textbookId );
            pstmt.setString( 2, chapterId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println( "Section Data for Textbook ID: " + textbookId + ", Chapter ID: " + chapterId );
                while ( rs.next() ) {
                    final String sectionId = rs.getString( "SectionID" );
                    final String title = rs.getString( "Title" );
                    final String hidden = rs.getString( "Hidden" );
                    System.out.println( "Section ID: " + sectionId + ", Title: " + title + ", Hidden: " + hidden );
                }
            }
        }
    }

    public static void deleteSectionData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId ) throws SQLException {
        final String deleteSQL = "DELETE FROM Section WHERE TextbookID = ? AND ChapterID = ? AND SectionID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, textbookId );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Section'." );
        }
    }

    public static void createBlockTable ( final Connection conn ) {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS Block (" + "TextbookID INT NOT NULL, "
                + "chapter_id VARCHAR(50) NOT NULL, " + "section_id VARCHAR(50) NOT NULL, "
                + "block_id VARCHAR(50) NOT NULL, " + "type VARCHAR(50) NOT NULL, " + "content VARCHAR(1000) NOT NULL, "
                + "hidden VARCHAR(10) DEFAULT 'no', " // Set default value to
                                                      // 'no'
                + "PRIMARY KEY (TextbookID, chapter_id, section_id, block_id)" + ")";

        try ( Statement stmt = conn.createStatement() ) {
            stmt.execute( createTableSQL );
            System.out.println( "Block table created successfully." );
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void insertBlockData ( final Connection conn, final String textbookId, final String chapterId,
            final String sectionId, final String blockId, final String type, final String content, final String hidden )
            throws SQLException {
        final String insertSQL = "INSERT INTO Block (TextbookID, chapter_id, section_id, block_id, type, content, hidden) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            // Convert textbookId from String to int
            final int textbookIdInt = Integer.parseInt( textbookId );

            pstmt.setInt( 1, textbookIdInt );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            pstmt.setString( 4, blockId );
            pstmt.setString( 5, type );
            pstmt.setString( 6, content );

            // Set hidden to NULL if it’s null to allow the default 'no' to
            // apply
            if ( hidden == null ) {
                pstmt.setNull( 7, java.sql.Types.VARCHAR );
            }
            else {
                pstmt.setString( 7, hidden );
            }

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Block'." );
        }
        catch ( final NumberFormatException e ) {
            System.out.println( "Invalid textbook ID: must be a numeric value." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'Block'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateBlockData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId, final String blockId, final String type, final String content, final String hidden )
            throws SQLException {
        final String updateSQL = "UPDATE Block SET type = ?, content = ?, hidden = ? WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, type );
            pstmt.setString( 2, content );
            pstmt.setString( 3, hidden );
            pstmt.setInt( 4, textbookId );
            pstmt.setString( 5, chapterId );
            pstmt.setString( 6, sectionId );
            pstmt.setString( 7, blockId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'Block'." );
        }
    }

    public static void queryBlockData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId ) throws SQLException {
        final String querySQL = "SELECT * FROM Block WHERE TextbookID = ? AND chapter_id = ? AND section_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setInt( 1, textbookId );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println( "Block Data for Textbook ID: " + textbookId + ", Chapter ID: " + chapterId
                        + ", Section ID: " + sectionId );
                while ( rs.next() ) {
                    final String blockId = rs.getString( "block_id" );
                    final String type = rs.getString( "type" );
                    final String content = rs.getString( "content" );
                    final String hidden = rs.getString( "hidden" );
                    System.out.println( "Block ID: " + blockId + ", Type: " + type + ", Content: " + content
                            + ", Hidden: " + hidden );
                }
            }
        }
    }

    public static void deleteBlockData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId, final String blockId ) throws SQLException {
        final String deleteSQL = "DELETE FROM Block WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, textbookId );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            pstmt.setString( 4, blockId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Block'." );
        }
    }

    public static void createActivityTable ( final Connection conn ) {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS Activity (" + "TextbookID INT NOT NULL, "
                + "chapter_id VARCHAR(50) NOT NULL, " + "section_id VARCHAR(50) NOT NULL, "
                + "block_id VARCHAR(50) NOT NULL, " + "unique_activity_id VARCHAR(50) NOT NULL, "
                + "hidden VARCHAR(10) DEFAULT 'no', " // Set default to 'no'
                + "PRIMARY KEY (TextbookID, chapter_id, section_id, block_id, unique_activity_id)" + ")";

        try ( Statement stmt = conn.createStatement() ) {
            stmt.execute( createTableSQL );
            System.out.println( "Activity table created successfully." );
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void insertActivityData ( final Connection conn, final String textbookId, final String chapterId,
            final String sectionId, final String blockId, final String uniqueActivityId, final String hidden )
            throws SQLException {
        final String insertSQL = "INSERT INTO Activity (TextbookID, chapter_id, section_id, block_id, unique_activity_id, hidden) VALUES (?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            // Convert textbookId from String to int
            final int textbookIdInt = Integer.parseInt( textbookId );

            pstmt.setInt( 1, textbookIdInt );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            pstmt.setString( 4, blockId );
            pstmt.setString( 5, uniqueActivityId );

            // Set hidden to NULL if it's explicitly null; otherwise, allow the
            // default 'no' to apply
            if ( hidden == null ) {
                pstmt.setNull( 6, java.sql.Types.VARCHAR );
            }
            else {
                pstmt.setString( 6, hidden );
            }

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Activity'." );
        }
        catch ( final NumberFormatException e ) {
            System.out.println( "Invalid textbook ID: must be a numeric value." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'Activity'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateActivityData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId, final String blockId, final String uniqueActivityId, final String hidden )
            throws SQLException {
        final String updateSQL = "UPDATE Activity SET hidden = ? WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, hidden );
            pstmt.setInt( 2, textbookId );
            pstmt.setString( 3, chapterId );
            pstmt.setString( 4, sectionId );
            pstmt.setString( 5, blockId );
            pstmt.setString( 6, uniqueActivityId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'Activity'." );
        }
    }

    public static void queryActivityData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId ) throws SQLException {
        final String querySQL = "SELECT * FROM Activity WHERE TextbookID = ? AND chapter_id = ? AND section_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setInt( 1, textbookId );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println( "Activity Data for Textbook ID: " + textbookId + ", Chapter ID: " + chapterId
                        + ", Section ID: " + sectionId );
                while ( rs.next() ) {
                    final String blockId = rs.getString( "block_id" );
                    final String uniqueActivityId = rs.getString( "unique_activity_id" );
                    final String hidden = rs.getString( "hidden" );
                    System.out.println(
                            "Block ID: " + blockId + ", Activity ID: " + uniqueActivityId + ", Hidden: " + hidden );
                }
            }
        }
    }

    public static void deleteActivityData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId, final String blockId, final String uniqueActivityId ) throws SQLException {
        final String deleteSQL = "DELETE FROM Activity WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, textbookId );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            pstmt.setString( 4, blockId );
            pstmt.setString( 5, uniqueActivityId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Activity'." );
        }
    }

    public static void createQuestionTable ( final Connection conn ) {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS Question (" + "TextbookID INT NOT NULL, "
                + "chapter_id VARCHAR(50) NOT NULL, " + "section_id VARCHAR(50) NOT NULL, "
                + "block_id VARCHAR(50) NOT NULL, " + "unique_activity_id VARCHAR(50) NOT NULL, "
                + "question_id VARCHAR(50) NOT NULL, " + "question_text TEXT NOT NULL, " + "option1 TEXT NOT NULL, "
                + "option1_explanation TEXT, " + "option2 TEXT NOT NULL, " + "option2_explanation TEXT, "
                + "option3 TEXT NOT NULL, " + "option3_explanation TEXT, " + "option4 TEXT NOT NULL, "
                + "option4_explanation TEXT, " + "answer INT NOT NULL, "
                + "PRIMARY KEY (TextbookID, chapter_id, section_id, block_id, unique_activity_id, question_id)" + ")";

        try ( Statement stmt = conn.createStatement() ) {
            stmt.execute( createTableSQL );
            System.out.println( "Question table created successfully." );
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void displayQuestion ( final Connection conn, final String blockID ) {
        final String querySQL = """
                SELECT question_text, option1, option2, option3, option4
                FROM Question
                WHERE block_id = ?;
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, blockID );

            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    final String questionText = rs.getString( "question_text" );
                    final String option1 = rs.getString( "option1" );
                    final String option2 = rs.getString( "option2" );
                    final String option3 = rs.getString( "option3" );
                    final String option4 = rs.getString( "option4" );

                    System.out.println( "\nQuestion: " + questionText );
                    System.out.println( "1. " + option1 );
                    System.out.println( "2. " + option2 );
                    System.out.println( "3. " + option3 );
                    System.out.println( "4. " + option4 );
                }
                else {
                    System.out.println( "No question found for this block." );
                }
            }
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void insertQuestionData ( final Connection conn, final String textbookId, final String chapterId,
            final String sectionId, final String blockId, final String uniqueActivityId, final String questionId,
            final String questionText, final String option1, final String option1Explanation, final String option2,
            final String option2Explanation, final String option3, final String option3Explanation,
            final String option4, final String option4Explanation, final int answer ) throws SQLException {
        final String insertSQL = "INSERT INTO Question (TextbookID, chapter_id, section_id, block_id, unique_activity_id, "
                + "question_id, question_text, option1, option1_explanation, option2, option2_explanation, option3, "
                + "option3_explanation, option4, option4_explanation, answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            // Convert textbookId from String to int
            final int textbookIdInt = Integer.parseInt( textbookId );

            pstmt.setInt( 1, textbookIdInt );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            pstmt.setString( 4, blockId );
            pstmt.setString( 5, uniqueActivityId );
            pstmt.setString( 6, questionId );
            pstmt.setString( 7, questionText );
            pstmt.setString( 8, option1 );
            pstmt.setString( 9, option1Explanation );
            pstmt.setString( 10, option2 );
            pstmt.setString( 11, option2Explanation );
            pstmt.setString( 12, option3 );
            pstmt.setString( 13, option3Explanation );
            pstmt.setString( 14, option4 );
            pstmt.setString( 15, option4Explanation );
            pstmt.setInt( 16, answer );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) inserted into 'Question'." );
        }
        catch ( final NumberFormatException e ) {
            System.out.println( "Invalid textbook ID: must be a numeric value." );
        }
        catch ( final SQLException e ) {
            if ( e.getErrorCode() == 1062 ) {
                System.out.println( "Record already exists in 'Question'. Skipping insertion." );
            }
            else {
                throw e;
            }
        }
    }

    public static void updateQuestionData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId, final String blockId, final String uniqueActivityId, final String questionId,
            final String questionText, final String option1, final String option1Explanation, final String option2,
            final String option2Explanation, final String option3, final String option3Explanation,
            final String option4, final String option4Explanation, final int answer ) throws SQLException {
        final String updateSQL = "UPDATE Question SET question_text = ?, option1 = ?, option1_explanation = ?, option2 = ?, option2_explanation = ?, option3 = ?, option3_explanation = ?, option4 = ?, option4_explanation = ?, answer = ? WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ? AND question_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, questionText );
            pstmt.setString( 2, option1 );
            pstmt.setString( 3, option1Explanation );
            pstmt.setString( 4, option2 );
            pstmt.setString( 5, option2Explanation );
            pstmt.setString( 6, option3 );
            pstmt.setString( 7, option3Explanation );
            pstmt.setString( 8, option4 );
            pstmt.setString( 9, option4Explanation );
            pstmt.setInt( 10, answer );
            pstmt.setInt( 11, textbookId );
            pstmt.setString( 12, chapterId );
            pstmt.setString( 13, sectionId );
            pstmt.setString( 14, blockId );
            pstmt.setString( 15, uniqueActivityId );
            pstmt.setString( 16, questionId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) updated in 'Question'." );
        }
    }

    public static void queryQuestionData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId, final String blockId, final String uniqueActivityId ) throws SQLException {
        final String querySQL = "SELECT * FROM Question WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setInt( 1, textbookId );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            pstmt.setString( 4, blockId );
            pstmt.setString( 5, uniqueActivityId );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println(
                        "Question Data for Textbook ID: " + textbookId + ", Chapter ID: " + chapterId + ", Section ID: "
                                + sectionId + ", Block ID: " + blockId + ", Activity ID: " + uniqueActivityId );
                while ( rs.next() ) {
                    final String questionId = rs.getString( "question_id" );
                    final String questionText = rs.getString( "question_text" );
                    final String option1 = rs.getString( "option1" );
                    final String option1Explanation = rs.getString( "option1_explanation" );
                    final String option2 = rs.getString( "option2" );
                    final String option2Explanation = rs.getString( "option2_explanation" );
                    final String option3 = rs.getString( "option3" );
                    final String option3Explanation = rs.getString( "option3_explanation" );
                    final String option4 = rs.getString( "option4" );
                    final String option4Explanation = rs.getString( "option4_explanation" );
                    final int answer = rs.getInt( "answer" );
                    System.out.println(
                            "Question ID: " + questionId + ", Question Text: " + questionText + ", Answer: " + answer );
                    // You can print options and explanations as needed
                }
            }
        }
    }

    public static void deleteQuestionData ( final Connection conn, final int textbookId, final String chapterId,
            final String sectionId, final String blockId, final String uniqueActivityId, final String questionId )
            throws SQLException {
        final String deleteSQL = "DELETE FROM Question WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ? AND question_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, textbookId );
            pstmt.setString( 2, chapterId );
            pstmt.setString( 3, sectionId );
            pstmt.setString( 4, blockId );
            pstmt.setString( 5, uniqueActivityId );
            pstmt.setString( 6, questionId );

            final int rowsAffected = pstmt.executeUpdate();
            System.out.println( rowsAffected + " row(s) deleted from 'Question'." );
        }
    }
    public static void hideContentBlock ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID ) throws SQLException {
        final String updateSQL = "UPDATE Block SET hidden = 'yes' WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Content block successfully marked as hidden." );
            }
            else {
                System.out.println( "No content block found with the provided ID." );
            }
        }
    }

    public static void deleteContentBlock ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID ) throws SQLException {
        final String deleteSQL = "DELETE FROM Block WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Content block successfully deleted." );
            }
            else {
                System.out.println( "No content block found with the provided ID." );
            }
        }
    }

    public static void insertActivity ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID, final String content )
            throws SQLException {
        final String insertSQL = "INSERT INTO Activity (TextbookID, chapter_id, section_id, block_id, unique_activity_id, hidden) VALUES (?, ?, ?, ?, ?, 'no')";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );
            pstmt.executeUpdate();
        }
    }

    public static void deleteActivity ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID ) throws SQLException {
        final String deleteSQL = "DELETE FROM Activity WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );
            pstmt.executeUpdate();
        }
    }

    public static void updateBlockContent ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String type, final String content )
            throws SQLException {
        final String updateSQL = "UPDATE Block SET content = ?, type = ? WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, content );
            pstmt.setString( 2, type );
            pstmt.setInt( 3, Integer.parseInt( textbookID ) );
            pstmt.setString( 4, chapterID );
            pstmt.setString( 5, sectionID );
            pstmt.setString( 6, blockID );
            pstmt.executeUpdate();
        }
    }

    public static void hideActivity ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID ) throws SQLException {
        final String updateSQL = "UPDATE Activity SET hidden = 'yes' WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            // Set query parameters
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );

            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Activity successfully marked as hidden." );
            }
            else {
                System.out.println( "No activity found with the provided ID." );
            }
        }
    }

    public static void insertActivityString ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID, final String content )
            throws SQLException {
        final String insertSQL = "INSERT INTO Activity (TextbookID, chapter_id, section_id, block_id, unique_activity_id, hidden) VALUES (?, ?, ?, ?, ?, 'no')";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );
            pstmt.executeUpdate();
        }
    }


    public static void hideContentBlock ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID ) throws SQLException {
        final String updateSQL = "UPDATE Block SET hidden = 'yes' WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Content block successfully marked as hidden." );
            }
            else {
                System.out.println( "No content block found with the provided ID." );
            }
        }
    }

    public static void deleteContentBlock ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID ) throws SQLException {
        final String deleteSQL = "DELETE FROM Block WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Content block successfully deleted." );
            }
            else {
                System.out.println( "No content block found with the provided ID." );
            }
        }
    }

    public static void insertActivity ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID, final String content )
            throws SQLException {
        final String insertSQL = "INSERT INTO Activity (TextbookID, chapter_id, section_id, block_id, unique_activity_id, hidden) VALUES (?, ?, ?, ?, ?, 'no')";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );
            pstmt.executeUpdate();
        }
    }

    public static void deleteActivity ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID ) throws SQLException {
        final String deleteSQL = "DELETE FROM Activity WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );
            pstmt.executeUpdate();
        }
    }

    public static void updateBlockContent ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String type, final String content )
            throws SQLException {
        final String updateSQL = "UPDATE Block SET content = ?, type = ? WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, content );
            pstmt.setString( 2, type );
            pstmt.setInt( 3, Integer.parseInt( textbookID ) );
            pstmt.setString( 4, chapterID );
            pstmt.setString( 5, sectionID );
            pstmt.setString( 6, blockID );
            pstmt.executeUpdate();
        }
    }

    public static void hideActivity ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID ) throws SQLException {
        final String updateSQL = "UPDATE Activity SET hidden = 'yes' WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            // Set query parameters
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );

            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Activity successfully marked as hidden." );
            }
            else {
                System.out.println( "No activity found with the provided ID." );
            }
        }
    }

    public static void insertActivityString ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID, final String content )
            throws SQLException {
        final String insertSQL = "INSERT INTO Activity (TextbookID, chapter_id, section_id, block_id, unique_activity_id, hidden) VALUES (?, ?, ?, ?, ?, 'no')";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );
            pstmt.executeUpdate();
        }
    }

    public static void hideContentBlock ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID ) throws SQLException {
        final String updateSQL = "UPDATE Block SET hidden = 'yes' WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Content block successfully marked as hidden." );
            }
            else {
                System.out.println( "No content block found with the provided ID." );
            }
        }
    }

    public static void deleteContentBlock ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID ) throws SQLException {
        final String deleteSQL = "DELETE FROM Block WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Content block successfully deleted." );
            }
            else {
                System.out.println( "No content block found with the provided ID." );
            }
        }
    }

    public static void insertActivity ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID, final String content )
            throws SQLException {
        final String insertSQL = "INSERT INTO Activity (TextbookID, chapter_id, section_id, block_id, unique_activity_id, hidden) VALUES (?, ?, ?, ?, ?, 'no')";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );
            pstmt.executeUpdate();
        }
    }

    public static void deleteActivity ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID ) throws SQLException {
        final String deleteSQL = "DELETE FROM Activity WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );
            pstmt.executeUpdate();
        }
    }

    public static void updateBlockContent ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String type, final String content )
            throws SQLException {
        final String updateSQL = "UPDATE Block SET content = ?, type = ? WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, content );
            pstmt.setString( 2, type );
            pstmt.setInt( 3, Integer.parseInt( textbookID ) );
            pstmt.setString( 4, chapterID );
            pstmt.setString( 5, sectionID );
            pstmt.setString( 6, blockID );
            pstmt.executeUpdate();
        }
    }

    public static void hideActivity ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID ) throws SQLException {
        final String updateSQL = "UPDATE Activity SET hidden = 'yes' WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND unique_activity_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            // Set query parameters
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );

            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Activity successfully marked as hidden." );
            }
            else {
                System.out.println( "No activity found with the provided ID." );
            }
        }
    }

    public static void insertActivityString ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID, final String blockID, final String uniqueActivityID, final String content )
            throws SQLException {
        final String insertSQL = "INSERT INTO Activity (TextbookID, chapter_id, section_id, block_id, unique_activity_id, hidden) VALUES (?, ?, ?, ?, ?, 'no')";
        try ( PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            pstmt.setString( 4, blockID );
            pstmt.setString( 5, uniqueActivityID );
            pstmt.executeUpdate();
        }
    }

}
