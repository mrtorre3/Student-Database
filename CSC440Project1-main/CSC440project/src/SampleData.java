import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class SampleData {

    public static void main ( final String[] args ) {
        final String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/kbradfo";
        final String user = "kbradfo";
        final String password = "kbradfo";
        final String csvFilePathTextbook = "src/data/textbook.csv";
        final String csvFilePathCourses = "src/data/courses.csv";
        final String csvFilePathFaculty = "src/data/faculty.csv";
        final String csvFilePathStudent = "src/data/students.csv";
        final String csvFilePathTA = "src/data/TA.csv";



        try ( Connection conn = DriverManager.getConnection( url, user, password ) ) {
            System.out.println( "Successfully connected to the database." );

            DBConnect.createETextbookTable( conn );
            insertETextbookData( conn, csvFilePathTextbook );
            
            DBConnect.createChapterTable( conn );
            insertChapterData( conn, csvFilePathTextbook );
            
            DBConnect.createSectionTable( conn );
            insertSectionData( conn, csvFilePathTextbook );

            DBConnect.createCourseTable( conn );
            insertCourseData( conn, csvFilePathCourses );

            DBConnect.createEnrollmentTable( conn );
            insertEnrollmentData( conn, csvFilePathCourses );

            DBConnect.createFacultyDetailsTable( conn );
            insertFacultyData( conn, csvFilePathFaculty );
            
            DBConnect.createBlockTable(conn);
            insertBlockData(conn, csvFilePathTextbook);

            DBConnect.createActivityTable(conn);
            insertActivityData(conn, csvFilePathTextbook);
            
            DBConnect.createQuestionTable(conn);
            insertQuestionData(conn, csvFilePathTextbook);
            
            DBConnect.createStudentTable(conn);
            insertStudentData(conn, csvFilePathStudent);
            
            DBConnect.createStudentCreditTable(conn);
            insertStudentCreditData(conn, csvFilePathStudent);
            
            DBConnect.createStudentQuestionsTable(conn);
            insertStudentQuestionsData(conn, csvFilePathTA);
            
            DBConnect.createTeachingAssistantTable(conn);
            insertTeachingAssistantData(conn, csvFilePathTA);
            
            
        }
        catch ( final SQLException e ) {
            e.printStackTrace();
        }
    }

    private static void insertETextbookData ( final Connection conn, final String csvFilePath ) {
        final String insertSQL = "INSERT IGNORE INTO ETextbook (TextbookID, Title) VALUES (?, ?)";

        try (
                BufferedReader br = new BufferedReader( new FileReader( csvFilePath ) );
                PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {

            String line;
            br.readLine(); // Skip the header row
            br.readLine();
            int rowCount = 0;
            while ( ( line = br.readLine() ) != null && rowCount < 3 ) {
                final String[] values = line.split( "," );

                if ( values.length >= 2 && !values[0].isEmpty() && !values[1].isEmpty() ) {
                    final int textbookId = Integer.parseInt( values[0].trim() );
                    final String title = values[1].trim();
                    rowCount++;

                    pstmt.setInt( 1, textbookId );
                    pstmt.setString( 2, title );

                    pstmt.addBatch();
                }
            }

            pstmt.executeBatch(); // Execute all the insert statements in batch
            System.out.println( "Data inserted successfully." );

        }
        catch ( IOException | SQLException e ) {
            e.printStackTrace();
        }
    }

    private static void insertChapterData ( final Connection conn, final String csvFilePath ) {
        final String insertSQL = "INSERT IGNORE INTO Chapter (TextbookID, ChapterID, Title, Hidden) VALUES (?, ?, ?, ?)";

        try (
                BufferedReader br = new BufferedReader( new FileReader( csvFilePath ) );
                PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            String line;
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            int rowCount = 0;

            while ( ( line = br.readLine() ) != null && rowCount < 5 ) {
                final String[] values = line.split( "," );

                if ( values.length >= 4 && !values[0].isEmpty() && !values[1].isEmpty() && !values[2].isEmpty() ) {
                    final int textbookId = Integer.parseInt( values[0].trim() );
                    final String chapterId = values[1].trim();
                    final String title = values[2].trim();
                    final String hidden = values[3].trim();
                    rowCount++;

                    pstmt.setInt( 1, textbookId );
                    pstmt.setString( 2, chapterId );
                    pstmt.setString( 3, title );
                    pstmt.setString( 4, hidden );

                    pstmt.addBatch();
                }
            }

            pstmt.executeBatch(); // Execute all the insert statements in batch
            System.out.println( "Chapter data inserted successfully." );

        }
        catch ( IOException | SQLException e ) {
            e.printStackTrace();
        }
    }

    private static void insertSectionData ( final Connection conn, final String csvFilePath ) {
        final String insertSQL = "INSERT IGNORE INTO Section (TextbookID, ChapterID, SectionID, Title, Hidden) VALUES (?, ?, ?, ?, ?)";

        try (
                BufferedReader br = new BufferedReader( new FileReader( csvFilePath ) );
                PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {
            String line;
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip
            br.readLine(); // Skip

            int rowCount = 0;
            while ( ( line = br.readLine() ) != null ) {
                final String[] values = line.split( "," );

                if ( values.length >= 5 && !values[0].isEmpty() && !values[1].isEmpty() && !values[2].isEmpty()
                        && rowCount < 10 ) {
                    final int textbookId = Integer.parseInt( values[0].trim() );
                    final String chapterId = values[1].trim();
                    final String sectionId = values[2].trim();
                    final String title = values[3].trim();
                    final String hidden = values[4].trim();

                    pstmt.setInt( 1, textbookId );
                    pstmt.setString( 2, chapterId );
                    pstmt.setString( 3, sectionId );
                    pstmt.setString( 4, title );
                    pstmt.setString( 5, hidden );

                    pstmt.addBatch();
                    rowCount++;
                }
            }

            pstmt.executeBatch(); // Execute all the insert statements in batch
            System.out.println( rowCount + " rows inserted successfully into the 'Section' table." );

        }
        catch ( IOException | SQLException e ) {
            e.printStackTrace();
        }
    }
    
    private static void insertBlockData(final Connection conn, final String csvFilePath) {
        final String insertSQL = "INSERT IGNORE INTO Block (TextbookID, chapter_id, section_id, block_id, type, content, hidden) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            String line;
            for (int i = 0; i < 32; i++) {
                br.readLine(); // Skip header lines as per requirement
            }

            int rowCount = 0;
            int batchSize = 100; // Set batch size for efficient inserts
            while ((line = br.readLine()) != null && rowCount < 10) {
                final String[] values = parseCSVLine(line);

                // Ensure the CSV has at least 6 fields and none are empty
                if (values.length >= 6 && 
                    !values[0].isEmpty() && 
                    !values[1].isEmpty() && 
                    !values[2].isEmpty() && 
                    !values[3].isEmpty() &&
                    !values[4].isEmpty() && 
                    !values[5].isEmpty()) {

                    // Skip invalid headers or non-numeric TextbookID
                    if (!isNumeric(values[0].trim())) {
                        System.err.println("Skipping invalid row: " + Arrays.toString(values));
                        continue;
                    }

                    final int textbookId = Integer.parseInt(values[0].trim());
                    final String chapterId = values[1].trim();
                    final String sectionId = values[2].trim();
                    final String blockId = values[3].trim();
                    final String type = values[4].trim();
                    final String content = values[5].trim();
                    
                    // Read 'hidden' field if available
                    String hidden = null;
                    if (values.length >= 7 && !values[6].trim().isEmpty()) {
                        hidden = values[6].trim();
                    }

                    pstmt.setInt(1, textbookId);
                    pstmt.setString(2, chapterId);
                    pstmt.setString(3, sectionId);
                    pstmt.setString(4, blockId);
                    pstmt.setString(5, type);
                    pstmt.setString(6, content);
                    
                    if (hidden != null) {
                        pstmt.setString(7, hidden);
                    } else {
                        pstmt.setNull(7, java.sql.Types.VARCHAR);
                    }

                    pstmt.addBatch();
                    rowCount++;

                    if (rowCount % batchSize == 0) {
                        pstmt.executeBatch(); // Execute in batches of batchSize
                    }
                } else {
                    // Handle cases where mandatory fields are missing
                    System.err.println("Skipping invalid row: " + Arrays.toString(values));
                }
            }

            pstmt.executeBatch(); // Execute the remaining batch
            System.out.println("Data inserted successfully.");

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // Helper method to parse a CSV line into fields, handling quoted fields
    private static String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        char[] chars = line.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '"') {
                inQuotes = !inQuotes; // Toggle state
                // Handle double quotes inside quoted field
                if (inQuotes && i + 1 < chars.length && chars[i + 1] == '"') {
                    sb.append('"'); // Add one quote
                    i++; // Skip the next quote
                }
            } else if (c == ',' && !inQuotes) {
                values.add(sb.toString());
                sb.setLength(0); // Reset the buffer
            } else {
                sb.append(c);
            }
        }
        values.add(sb.toString());
        return values.toArray(new String[0]);
    }

    // Helper method to check if a string is numeric
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void insertActivityData(final Connection conn, final String csvFilePath) {
        final String insertSQL = "INSERT IGNORE INTO Activity (TextbookID, chapter_id, section_id, block_id, unique_activity_id, hidden) VALUES (?, ?, ?, ?, ?, ?)";

        try (
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            String line;
            for (int i = 0; i < 47; i++) {
                br.readLine(); // Skip header lines as per requirement
            }

            int rowCount = 0;
            int batchSize = 100; // Set batch size for efficient inserts
            while ((line = br.readLine()) != null && rowCount < 3) {
                final String[] values = parseCSVLine(line);

                // Ensure the CSV has at least 5 fields and mandatory fields are not empty
                if (values.length >= 5 &&
                    !values[0].isEmpty() &&
                    !values[1].isEmpty() &&
                    !values[2].isEmpty() &&
                    !values[3].isEmpty() &&
                    !values[4].isEmpty()) {

                    // Skip invalid headers or non-numeric TextbookID
                    if (!isNumeric(values[0].trim())) {
                        System.err.println("Skipping invalid row: " + Arrays.toString(values));
                        continue;
                    }

                    final int textbookId = Integer.parseInt(values[0].trim());
                    final String chapterId = values[1].trim();
                    final String sectionId = values[2].trim();
                    final String blockId = values[3].trim();
                    final String uniqueActivityId = values[4].trim();

                    // Read 'hidden' field if available
                    String hidden = null;
                    if (values.length >= 6 && !values[5].trim().isEmpty()) {
                        hidden = values[5].trim();
                    }

                    pstmt.setInt(1, textbookId);
                    pstmt.setString(2, chapterId);
                    pstmt.setString(3, sectionId);
                    pstmt.setString(4, blockId);
                    pstmt.setString(5, uniqueActivityId);

                    if (hidden != null) {
                        pstmt.setString(6, hidden);
                    } else {
                        pstmt.setNull(6, java.sql.Types.VARCHAR);
                    }

                    pstmt.addBatch();
                    rowCount++;

                    if (rowCount % batchSize == 0) {
                        pstmt.executeBatch(); // Execute in batches of batchSize
                    }
                } else {
                    // Handle cases where mandatory fields are missing
                    System.err.println("Skipping invalid row: " + Arrays.toString(values));
                }
            }

            pstmt.executeBatch(); // Execute the remaining batch
            System.out.println("Activity data inserted successfully.");

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }


    private static void insertQuestionData(final Connection conn, final String csvFilePath) {
        final String insertSQL = "INSERT IGNORE INTO Question (TextbookID, chapter_id, section_id, block_id, unique_activity_id, question_id, question_text, option1, option1_explanation, option2, option2_explanation, option3, option3_explanation, option4, option4_explanation, answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            String line;
            for (int i = 0; i < 56; i++) {
                br.readLine(); // Skip header lines as per requirement
            }

            int rowCount = 0;
            int batchSize = 100; // Set batch size for efficient inserts
            while ((line = br.readLine()) != null && rowCount < 9) {
                final String[] values = parseCSVLine(line);

                // Ensure the CSV has at least 16 fields and mandatory fields are not empty
                if (values.length >= 16 &&
                    !values[0].isEmpty() && // textbook_id
                    !values[1].isEmpty() && // chapter_id
                    !values[2].isEmpty() && // section_id
                    !values[3].isEmpty() && // block_id
                    !values[4].isEmpty() && // unique_activity_id
                    !values[5].isEmpty() && // question_id
                    !values[6].isEmpty() && // question_text
                    !values[7].isEmpty() && // option1
                    !values[9].isEmpty() && // option2
                    !values[11].isEmpty() && // option3
                    !values[13].isEmpty() && // option4
                    !values[15].isEmpty()) { // answer

                    // Skip invalid headers or non-numeric TextbookID
                    if (!isNumeric(values[0].trim())) {
                        System.err.println("Skipping invalid row: " + Arrays.toString(values));
                        continue;
                    }

                    final int textbookId = Integer.parseInt(values[0].trim());
                    final String chapterId = values[1].trim();
                    final String sectionId = values[2].trim();
                    final String blockId = values[3].trim();
                    final String uniqueActivityId = values[4].trim();
                    final String questionId = values[5].trim();
                    final String questionText = values[6].trim();

                    final String option1 = values[7].trim();
                    String option1Explanation = values[8].trim().isEmpty() ? null : values[8].trim();

                    final String option2 = values[9].trim();
                    String option2Explanation = values[10].trim().isEmpty() ? null : values[10].trim();

                    final String option3 = values[11].trim();
                    String option3Explanation = values[12].trim().isEmpty() ? null : values[12].trim();

                    final String option4 = values[13].trim();
                    String option4Explanation = values[14].trim().isEmpty() ? null : values[14].trim();

                    int answer;
                    try {
                        answer = Integer.parseInt(values[15].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid answer value in row: " + Arrays.toString(values));
                        continue;
                    }

                    pstmt.setInt(1, textbookId);
                    pstmt.setString(2, chapterId);
                    pstmt.setString(3, sectionId);
                    pstmt.setString(4, blockId);
                    pstmt.setString(5, uniqueActivityId);
                    pstmt.setString(6, questionId);
                    pstmt.setString(7, questionText);
                    pstmt.setString(8, option1);

                    if (option1Explanation != null) {
                        pstmt.setString(9, option1Explanation);
                    } else {
                        pstmt.setNull(9, java.sql.Types.VARCHAR);
                    }

                    pstmt.setString(10, option2);

                    if (option2Explanation != null) {
                        pstmt.setString(11, option2Explanation);
                    } else {
                        pstmt.setNull(11, java.sql.Types.VARCHAR);
                    }

                    pstmt.setString(12, option3);

                    if (option3Explanation != null) {
                        pstmt.setString(13, option3Explanation);
                    } else {
                        pstmt.setNull(13, java.sql.Types.VARCHAR);
                    }

                    pstmt.setString(14, option4);

                    if (option4Explanation != null) {
                        pstmt.setString(15, option4Explanation);
                    } else {
                        pstmt.setNull(15, java.sql.Types.VARCHAR);
                    }

                    pstmt.setInt(16, answer);

                    pstmt.addBatch();
                    rowCount++;

                    if (rowCount % batchSize == 0) {
                        pstmt.executeBatch(); // Execute in batches of batchSize
                    }
                } else {
                    // Handle cases where mandatory fields are missing
                    System.err.println("Skipping invalid row: " + Arrays.toString(values));
                }
            }

            pstmt.executeBatch(); // Execute the remaining batch
            System.out.println("Question data inserted successfully.");

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
    
    private static void insertStudentData(final Connection conn, final String csvFilePath) {
        final String insertSQL = "INSERT IGNORE INTO Student (student_id, full_name, password, email) VALUES (?, ?, ?, ?)";

        try (
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            String line;
            for (int i = 0; i < 3; i++) {
                br.readLine();
            }

            int rowCount = 0;
            int batchSize = 100; // Set batch size for efficient inserts
            while ((line = br.readLine()) != null && rowCount < 9) {
                final String[] values = parseCSVLine(line);

                // Ensure the CSV has exactly 4 fields and mandatory fields are not empty
                if (values.length >= 4 &&
                    !values[0].isEmpty() && // student_id
                    !values[1].isEmpty() && // full_name
                    !values[2].isEmpty() && // password
                    !values[3].isEmpty()) { // email

                    final String studentId = values[0].trim();
                    final String fullName = values[1].trim();
                    final String password = values[2].trim();
                    final String email = values[3].trim();

                    pstmt.setString(1, studentId);
                    pstmt.setString(2, fullName);
                    pstmt.setString(3, password);
                    pstmt.setString(4, email);

                    pstmt.addBatch();
                    rowCount++;

                    if (rowCount % batchSize == 0) {
                        pstmt.executeBatch(); // Execute in batches of batchSize
                    }
                } else {
                    // Handle cases where mandatory fields are missing
                    System.err.println("Skipping invalid row: " + Arrays.toString(values));
                }
            }

            pstmt.executeBatch(); // Execute the remaining batch
            System.out.println("Student data inserted successfully.");

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }


    private static void insertStudentCreditData(final Connection conn, final String csvFilePath) {
        final String insertSQL = "INSERT IGNORE INTO StudentCredit (student_id, registered_course_id, total_participation_points, num_of_finished_activities) VALUES (?, ?, ?, ?)";

        try (
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            String line;
            for (int i = 0; i < 16; i++) {
                br.readLine();
            }

            int rowCount = 0;
            int batchSize = 100; // Set batch size for efficient inserts
            while ((line = br.readLine()) != null && rowCount < 11) {
                final String[] values = parseCSVLine(line);

                // Ensure the CSV has exactly 4 fields and mandatory fields are not empty
                if (values.length >= 4 &&
                    !values[0].isEmpty() && // student_id
                    !values[1].isEmpty() && // registered_course_id
                    !values[2].isEmpty() && // total_participation_points
                    !values[3].isEmpty()) { // num_of_finished_activities

                    final String studentId = values[0].trim();
                    final String registeredCourseId = values[1].trim();

                    int totalParticipationPoints;
                    int numOfFinishedActivities;

                    try {
                        totalParticipationPoints = Integer.parseInt(values[2].trim());
                        numOfFinishedActivities = Integer.parseInt(values[3].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number in row: " + Arrays.toString(values));
                        continue;
                    }

                    pstmt.setString(1, studentId);
                    pstmt.setString(2, registeredCourseId);
                    pstmt.setInt(3, totalParticipationPoints);
                    pstmt.setInt(4, numOfFinishedActivities);

                    pstmt.addBatch();
                    rowCount++;

                    if (rowCount % batchSize == 0) {
                        pstmt.executeBatch(); // Execute in batches of batchSize
                    }
                } else {
                    // Handle cases where mandatory fields are missing
                    System.err.println("Skipping invalid row: " + Arrays.toString(values));
                }
            }

            pstmt.executeBatch(); // Execute the remaining batch
            System.out.println("StudentCredit data inserted successfully.");

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
    
    private static void insertStudentQuestionsData(final Connection conn, final String csvFilePath) {
        final String insertSQL = "INSERT IGNORE INTO StudentQuestions (student_id, course_id, textbook_id, chapter_id, section_id, block_id, unique_activity_id, question_id, point, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
        
            String line;
            for (int i = 0; i < 30; i++) {
                br.readLine(); 
            }
        
            int rowCount = 0;
            int batchSize = 100; // Set batch size for efficient inserts
            while ((line = br.readLine()) != null  && rowCount < 13) {
                final String[] values = parseCSVLine(line);
        
                // Ensure the CSV has at least 10 fields and mandatory fields are not empty
                if (values.length >= 9 &&
                    !values[0].isEmpty() && // student_id
                    !values[1].isEmpty() && // course_id
                    !values[2].isEmpty() && // textbook_id
                    !values[3].isEmpty() && // chapter_id
                    !values[4].isEmpty() && // section_id
                    !values[5].isEmpty() && // block_id
                    !values[6].isEmpty() && // unique_activity_id
                    !values[7].isEmpty() && // question_id
                    !values[8].isEmpty()) { // point
        
                    final String studentId = values[0].trim();
                    final String course_id = values[1].trim();
        
                    int textbookId;
                    try {
                        textbookId = Integer.parseInt(values[2].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid textbook_id in row: " + Arrays.toString(values));
                        continue;
                    }
        
                    final String chapterId = values[3].trim();
                    final String sectionId = values[4].trim();
                    final String blockId = values[5].trim();
                    final String uniqueActivityId = values[6].trim();
                    final String questionId = values[7].trim();
        
                    int point;
                    try {
                        point = Integer.parseInt(values[8].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid point value in row: " + Arrays.toString(values));
                        continue;
                    }
        
                    // Parse timestamp if available
                    Timestamp timestamp = null;
                    if (values.length >= 10 && !values[9].trim().isEmpty()) {
                        try {
                            timestamp = parseTimestamp(values[9].trim());
                        } catch (IllegalArgumentException e) {
                            System.err.println("Invalid timestamp in row: " + Arrays.toString(values));
                            continue;
                        }
                    } else {
                        timestamp = new Timestamp(System.currentTimeMillis()); // Use current time
                    }
        
                    pstmt.setString(1, studentId);
                    pstmt.setString(2, course_id);
                    pstmt.setInt(3, textbookId);
                    pstmt.setString(4, chapterId);
                    pstmt.setString(5, sectionId);
                    pstmt.setString(6, blockId);
                    pstmt.setString(7, uniqueActivityId);
                    pstmt.setString(8, questionId);
                    pstmt.setInt(9, point);
                    pstmt.setTimestamp(10, timestamp);
        
                    pstmt.addBatch();
                    rowCount++;
        
                    if (rowCount % batchSize == 0) {
                        pstmt.executeBatch(); // Execute in batches of batchSize
                    }
                } else {
                    // Handle cases where mandatory fields are missing
                    System.err.println("Skipping invalid row: " + Arrays.toString(values));
                }
            }
        
            pstmt.executeBatch(); // Execute the remaining batch
            System.out.println("StudentQuestions data inserted successfully.");
        
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
    
    private static Timestamp parseTimestamp(String timestampStr) {
        // Expected format: "2024.08.01-11:10"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(timestampStr, formatter);
        return Timestamp.valueOf(dateTime);
    }

    private static void insertTeachingAssistantData(final Connection conn, final String csvFilePath) {
        final String insertSQL = "INSERT IGNORE INTO TeachingAssistant (TA_id, first_name, last_name, email, password, course_id, faculty_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            String line;
            br.readLine(); // Skip header row
            br.readLine(); // Skip column names row

            int rowCount = 0;
            int batchSize = 100; // Set batch size for efficient inserts
            StringBuilder record = new StringBuilder();

            while ((line = br.readLine()) != null) {
                // Handle cases where TA_id contains newline by accumulating lines
                if (record.length() > 0) {
                    record.append("\n"); // Add a newline for multiline records
                }
                record.append(line.trim());

                // Check if we have a complete record by splitting and counting the fields
                String[] values = record.toString().split(",");
                if (values.length == 7) {
                    // Proceed with insertion if all fields are populated
                    if (!values[0].isEmpty() && !values[1].isEmpty() && !values[2].isEmpty()
                            && !values[3].isEmpty() && !values[4].isEmpty()
                            && !values[5].isEmpty() && !values[6].isEmpty()) {

                        pstmt.setString(1, values[0].trim());
                        pstmt.setString(2, values[1].trim());
                        pstmt.setString(3, values[2].trim());
                        pstmt.setString(4, values[3].trim());
                        pstmt.setString(5, values[4].trim());
                        pstmt.setString(6, values[5].trim());
                        pstmt.setString(7, values[6].trim());

                        pstmt.addBatch();
                        rowCount++;
                        
                        if (rowCount % batchSize == 0) {
                            pstmt.executeBatch();
                        }
                    } else {
                        System.err.println("Skipping invalid row due to missing data: " + Arrays.toString(values));
                    }
                    // Reset the record StringBuilder after processing a complete row
                    record.setLength(0);
                }
            }

            pstmt.executeBatch(); // Execute any remaining batched statements
            System.out.println("TeachingAssistant data inserted successfully.");

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }



    


    private static void insertCourseData ( final Connection conn, final String csvFilePath ) {
        final String insertSQL = "INSERT IGNORE INTO Course (course_id, CourseName, ETextbook, CourseType, faculty_id, "
                + "TAID, StartDate, EndDate, Token, CourseCapacity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                BufferedReader br = new BufferedReader( new FileReader( csvFilePath ) );
                PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {

            String line;
            br.readLine(); // Skip the header row
            final SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy" );
            final StringBuilder record = new StringBuilder();

            while ( ( line = br.readLine() ) != null ) {
                // Accumulate lines in case the record spans multiple lines
                if ( record.length() > 0 ) {
                    record.append( "\n" ); // Add a newline if it's a
                                           // continuation
                }
                record.append( line );

                // Check if we have a complete record by splitting and counting
                // the fields
                final String[] values = record.toString().split( "," );
                if ( values.length == 10 ) {
                    if ( !values[0].isEmpty() && !values[1].isEmpty() ) {
                        final String courseID = values[0].trim();
                        final String courseName = values[1].trim();
                        final String eTextbook = values[2].trim();
                        final String courseType = values[3].trim();
                        final String faculty_id = values[4].trim();
                        final String taID = values[5].trim();
                        final java.sql.Date startDate = new java.sql.Date(
                                dateFormat.parse( values[6].trim() ).getTime() );
                        final java.sql.Date endDate = new java.sql.Date(
                                dateFormat.parse( values[7].trim() ).getTime() );
                        final String token = values[8].trim().equals( "x" ) ? null : values[8].trim();
                        final Integer courseCapacity = values[9].trim().equals( "x" ) ? null
                                : Integer.parseInt( values[9].trim() );

                        pstmt.setString( 1, courseID );
                        pstmt.setString( 2, courseName );
                        pstmt.setString( 3, eTextbook );
                        pstmt.setString( 4, courseType );
                        pstmt.setString( 5, faculty_id );
                        pstmt.setString( 6, taID );
                        pstmt.setDate( 7, startDate );
                        pstmt.setDate( 8, endDate );
                        pstmt.setString( 9, token );
                        if ( courseCapacity != null ) {
                            pstmt.setInt( 10, courseCapacity );
                        }
                        else {
                            pstmt.setNull( 10, java.sql.Types.INTEGER );
                        }

                        pstmt.addBatch();
                    }

                    // Clear the record StringBuilder for the next entry
                    record.setLength( 0 );
                }
            }

            pstmt.executeBatch(); // Execute all the insert statements in batch
            System.out.println( "Course data inserted successfully." );

        }
        catch ( IOException | SQLException | ParseException e ) {
            e.printStackTrace();
        }
    }

    public static void insertEnrollmentData ( final Connection conn, final String csvFilePath ) {
        final String insertSQL = "INSERT IGNORE INTO Enrollment (course_id, student_id, Status) VALUES (?, ?, ?)";

        try (
                BufferedReader br = new BufferedReader( new FileReader( csvFilePath ) );
                PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {

            String line;
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();

            while ( ( line = br.readLine() ) != null ) {
                final String[] values = line.split( "," );

                if ( values.length >= 3 && !values[0].isEmpty() && !values[1].isEmpty() && !values[2].isEmpty() ) {
                    final String course_id = values[0].trim();
                    final String student_id = values[1].trim();
                    final String status = values[2].trim();

                    pstmt.setString( 1, course_id );
                    pstmt.setString( 2, student_id );
                    pstmt.setString( 3, status );

                    pstmt.addBatch();
                }
            }

            pstmt.executeBatch(); // Execute all the insert statements in batch
            System.out.println( "Enrollment data inserted successfully." );

        }
        catch ( IOException | SQLException e ) {
            e.printStackTrace();
        }
    }

    // Method to insert data from the CSV file into the FacultyDetails table
    private static void insertFacultyData ( final Connection conn, final String csvFilePath ) {
        final String insertSQL = "INSERT IGNORE INTO FacultyDetails (faculty_id, FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?, ?)";

        try (
                BufferedReader br = new BufferedReader( new FileReader( csvFilePath ) );
                PreparedStatement pstmt = conn.prepareStatement( insertSQL ) ) {

            String line;
            br.readLine(); // Skip the first header row
            br.readLine(); // Skip the second header row with column names

            while ( ( line = br.readLine() ) != null ) {
                final String[] values = line.split( "," );

                if ( values.length >= 5 && !values[0].isEmpty() && !values[1].isEmpty() ) {
                    final String faculty_id = values[0].trim();
                    final String firstName = values[1].trim();
                    final String lastName = values[2].trim();
                    final String email = values[3].trim();
                    final String password = values[4].trim();

                    pstmt.setString( 1, faculty_id );
                    pstmt.setString( 2, firstName );
                    pstmt.setString( 3, lastName );
                    pstmt.setString( 4, email );
                    pstmt.setString( 5, password );

                    pstmt.addBatch();
                }
            }

            pstmt.executeBatch(); // Execute all the insert statements in batch
            System.out.println( "Faculty data inserted successfully." );

        }
        catch ( IOException | SQLException e ) {
            e.printStackTrace();
        }
    }

}
