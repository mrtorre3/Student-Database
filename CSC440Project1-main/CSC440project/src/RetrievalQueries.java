
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrievalQueries {
    public static void main ( final String[] args ) {
        final String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/arjackmo";
        final String user = "arjackmo";
        final String password = "200409231";

        try ( Connection conn = DriverManager.getConnection( url, user, password ) ) {
            System.out.println( "Successfully connected to the database." );

            countSectionsInFirstChapter( conn );
            printFacultyAndTAs( conn );
            fetchActiveCourseDetails( conn );
            findCourseWithLargestWaitingList( conn );
            printChapterContents( conn );
            printIncorrectAnswers( conn );
            findBooksWithDifferentStatuses( conn );
        }
        catch ( final SQLException e ) {
            e.printStackTrace();
        }
    }

    // Method to count the number of sections in the first chapter
    public static void countSectionsInFirstChapter ( final Connection conn ) {
        // SQL query to count sections
        final String querySQL = "SELECT COUNT(*) AS NumberOfSections " + "FROM Section "
                + "WHERE ChapterID = 'chap01' AND TextbookID = '101';";

        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {

            // Execute the query
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    // Retrieve and print the count
                    final int numberOfSections = rs.getInt( "NumberOfSections" );
                    System.out.println( "Number of sections in the first chapter: " + numberOfSections );
                }
                else {
                    System.out.println( "No sections found for the first chapter." );
                }
            }
        }
        catch ( final SQLException e ) {
            e.printStackTrace(); // Print exception details
        }
    }

    public static void printFacultyAndTAs ( final Connection conn ) {
        final String query = "SELECT " + "    CONCAT(f.FirstName, ' ', f.LastName) AS Name, 'Faculty' AS Role "
                + "FROM FacultyDetails f " + "UNION ALL " + "SELECT "
                + "    CONCAT(ta.first_name, ' ', ta.last_name) AS Name, 'TA' AS Role " + "FROM TeachingAssistant ta;";
        try ( PreparedStatement pstmt = conn.prepareStatement( query ) ) {
            final ResultSet rs = pstmt.executeQuery();
            System.out.println( "Name\tRole" );
            while ( rs.next() ) {
                final String name = rs.getString( "Name" );
                final String role = rs.getString( "Role" );
                System.out.println( name + "\t" + role );
            }
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void fetchActiveCourseDetails ( final Connection conn ) {
        final String query = """
                    SELECT c.course_id, CONCAT(f.FirstName, ' ', f.LastName) AS FacultyName, COUNT(s.student_id) AS TotalStudents
                    FROM Course c
                    JOIN FacultyDetails f ON c.faculty_id = f.faculty_id
                    LEFT JOIN Enrollment e ON c.course_id = e.course_id
                    LEFT JOIN Student s ON e.student_id = s.student_id
                    WHERE c.CourseType = 'Active'
                    GROUP BY c.course_id, FacultyName;
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( query ); ResultSet rs = pstmt.executeQuery() ) {

            System.out.println( "Course ID | Faculty Name         | Total Students" );
            System.out.println( "-------------------------------------------------" );

            while ( rs.next() ) {
                final String courseId = rs.getString( "course_id" );
                final String facultyName = rs.getString( "FacultyName" );
                final int totalStudents = rs.getInt( "TotalStudents" );

                System.out.printf( "%-10s | %-20s | %-15d%n", courseId, facultyName, totalStudents );
            }
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void findCourseWithLargestWaitingList ( final Connection conn ) {
        final String query = """
                    SELECT course_id, COUNT(*) AS TotalPending
                    FROM Enrollment
                    WHERE Status = 'Pending'
                    GROUP BY course_id
                    ORDER BY TotalPending DESC
                    LIMIT 1;
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( query ); ResultSet rs = pstmt.executeQuery() ) {

            if ( rs.next() ) {
                final String courseId = rs.getString( "course_id" );
                final int totalPending = rs.getInt( "TotalPending" );

                System.out.println( "Course with Largest Waiting List:" );
                System.out.println( "Course ID: " + courseId );
                System.out.println( "Total Pending Students: " + totalPending );
            }
            else {
                System.out.println( "No courses with students on the waiting list." );
            }

        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void printChapterContents ( final Connection conn ) {
        final String query = """
                    SELECT TextbookID, chapter_id, section_id, block_id, type, content
                    FROM Block
                    WHERE TextbookID = '101' AND chapter_id = 'chap02'
                    ORDER BY section_id, block_id;
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( query ) ) {

            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println( "Contents of Chapter 02 for Textbook 101:" );
                System.out.println( "-------------------------------------------------------------" );

                while ( rs.next() ) {
                    final String sectionId = rs.getString( "section_id" );
                    final String blockId = rs.getString( "block_id" );
                    final String type = rs.getString( "type" );
                    final String content = rs.getString( "content" );

                    System.out.printf( "Section: %s | Block: %s | Type: %s%nContent: %s%n%n", sectionId, blockId, type,
                            content );
                }
            }
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    public static void printIncorrectAnswers ( final Connection conn ) {
        final String query = """
                    SELECT option1, option1_explanation, option2, option2_explanation, option3, option3_explanation, option4, option4_explanation, answer
                    FROM Question
                    WHERE TextbookID = 101 AND chapter_id = 'chap01' AND section_id = 'Sec02' AND unique_activity_id = 'ACT0' AND question_id = 'Q2';
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( query ); ResultSet rs = pstmt.executeQuery() ) {

            if ( rs.next() ) {
                final int correctAnswer = rs.getInt( "answer" );

                // Iterate through all options and print incorrect ones
                for ( int i = 1; i <= 4; i++ ) {
                    if ( i != correctAnswer ) {
                        final String option = rs.getString( "option" + i );
                        final String explanation = rs.getString( "option" + i + "_explanation" );

                        System.out.printf( "Incorrect Option %d: %s%nExplanation: %s%n%n", i, option, explanation );
                    }
                }
            }
            else {
                System.out.println( "No data found for the specified question." );
            }

        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    // There aren't any
    public static void findBooksWithDifferentStatuses ( final Connection conn ) {
        final String query = """
                    SELECT c1.ETextbook, c1.faculty_id AS ActiveInstructor, c2.faculty_id AS EvaluationInstructor
                    FROM Course c1
                    JOIN Course c2 ON c1.ETextbook = c2.ETextbook
                    WHERE c1.CourseType = 'Active' AND c2.CourseType = 'Evaluation' AND c1.faculty_id != c2.faculty_id;
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( query ); ResultSet rs = pstmt.executeQuery() ) {

            System.out.println( "Books with Different Instructors for Active and Evaluation Statuses:" );
            System.out.println( "---------------------------------------------------------------" );

            while ( rs.next() ) {
                final String eTextbook = rs.getString( "ETextbook" );
                final String activeInstructor = rs.getString( "ActiveInstructor" );
                final String evaluationInstructor = rs.getString( "EvaluationInstructor" );

                System.out.printf( "ETextbook: %s | Active Instructor: %s | Evaluation Instructor: %s%n", eTextbook,
                        activeInstructor, evaluationInstructor );
            }

        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }
}
