

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UI {
	
	public static String permission; 
    private static String userID; // To store the logged-in user's ID

    public static void main ( final String[] args ) {
        final String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/kbradfo";
        final String user = "kbradfo";
        final String password = "kbradfo";

        // Using try-with-resources for Connection
        try ( Connection conn = DriverManager.getConnection( url, user, password ) ) {
            final Scanner scanner = new Scanner( System.in );
            boolean exit = false;

            while ( !exit ) {

                // // // Empty the DB
                DBConnect.dropTables( conn );
                // // Create new Tables
                DBConnect.createStudentTable( conn );
                DBConnect.createFacultyDetailsTable( conn );
                DBConnect.createEnrollmentTable( conn );
                DBConnect.createCourseTable( conn );
                DBConnect.createETextbookTable( conn );
                DBConnect.createChapterTable( conn );
                DBConnect.createSectionTable( conn );
                DBConnect.createBlockTable( conn );
                DBConnect.createActivityTable( conn );
                DBConnect.createQuestionTable( conn );
                DBConnect.createStudentCreditTable( conn );
                DBConnect.createStudentQuestionsTable( conn );
                DBConnect.createTeachingAssistantTable( conn );

                // Display the main menu
                showMainMenu();

                // Get user's choice
                final int choice = getUserChoice( scanner, 5 );

                // Process user's choice
                switch ( choice ) {
                    case 1:
                        if ( login( "Admin", scanner ) ) {
                        	permission = "admin";
                        	showAdminLoginMenu( scanner, conn );
                           
                        }
                        break;
                    case 2:
                        if ( login( "Faculty", scanner ) ) {
                            permission = "faculty";

                            showFacultyLoginMenu( scanner, conn );
                        }
                        break;
                    case 3:
                        if ( login( "TA", scanner ) ) {
                            permission = "TA";

                            showTALoginMenu( scanner, conn );
                        }
                        break;
                    case 4:
                        
			    showStudentLoginMenu( scanner, conn );
                            permission = "student";
                        
                        break;
                    case 5:
                        System.out.println( "Exiting the system. Goodbye!" );
                        exit = true;
                        break;
                    default:
                        System.out.println( "Invalid choice. Please enter a valid option." );
                }
            }

            scanner.close();
        }
        catch ( final SQLException e ) {
            e.printStackTrace();
        }

    }

    private static void showMainMenu () {
        System.out.println( "Application Menu:" );
        System.out.println( "1. Admin Login" );
        System.out.println( "2. Faculty Login" );
        System.out.println( "3. TA Login" );
        System.out.println( "4. Student Login" );
        System.out.println( "5. Exit" );
    }

    /**
     * ADMIN OPERATIONS START
     *
     */

    private static void showAdminLoginMenu ( final Scanner scanner, final Connection conn ) {
        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\nAdmin Menu:" );
            System.out.println( "1. Sign In" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    showAdminOperationsMenu( scanner, conn );
                    break;
                case 2:
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void showFacultyLoginMenu ( final Scanner scanner, final Connection conn ) {
        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\nFaculty Menu:" );
            System.out.println( "1. Sign In" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    showFacultyOperationsMenu( scanner, conn );
                    break;
                case 2:
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void showStudentLoginMenu ( final Scanner scanner, final Connection conn ) {
        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\nStudent Menu:" );
            System.out.println( "1. Enroll in a course" );
            System.out.println( "2. Sign In" );
            System.out.println( "3. Go Back" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:
                    addStudentToCourse( scanner, conn );
                    break;
                case 2:
                    if ( login( "Student", scanner ) ) {
                        showStudentOperationsMenu( scanner, conn );
                    }
                case 3:
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void showTALoginMenu ( final Scanner scanner, final Connection conn ) {
        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\nTeaching Assistant Menu:" );
            System.out.println( "1. Sign In" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    showTAOperationsMenu( scanner, conn );
                    break;
                case 2:
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void showAdminOperationsMenu ( final Scanner scanner, final Connection conn ) {
        boolean logout = false;

        while ( !logout ) {
            System.out.println( "\nAdmin Operations Menu:" );
            System.out.println( "1. Create a Faculty Account" );
            System.out.println( "2. Create E-textbook" );
            System.out.println( "3. Modify E-textbooks" );
            System.out.println( "4. Create New Active Course" );
            System.out.println( "5. Create New Evaluation Course" );
            System.out.println( "6. Logout" );

            final int choice = getUserChoice( scanner, 6 );

            switch ( choice ) {
                case 1:
                    createFacultyAccount( scanner, conn );
                    break;
                case 2:
                    createETextbook( scanner, conn );
                    break;
                case 3:
                    modifyETextbook( scanner, conn );
                    break;
                case 4:
                    createNewActiveCourse( scanner, conn );
                    break;
                case 5:
                    createNewEvaluationCourse( scanner, conn );
                    break;
                case 6:
                    System.out.println( "\nLogging out from Admin...\n" );
                    logout = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void showFacultyOperationsMenu ( final Scanner scanner, final Connection conn ) {
        boolean logout = false;

        while ( !logout ) {
            System.out.println( "\nFaculty Operations Menu:" );
            System.out.println( "1. Go To Active Course" );
            System.out.println( "2. Go To Evaluation Course" );
            System.out.println( "3. View Courses" );
            System.out.println( "4. Change Password" );
            System.out.println( "5. Logout" );

            final int choice = getUserChoice( scanner, 5 );
            switch ( choice ) {
                case 1:
                    goToActiveCourseFac( scanner, conn );
                    break;
                case 2:
                    goToEvaluationCourse( scanner, conn );
                    break;
                case 3:
                    viewCourses( scanner, conn );
                    break;
                case 4:
                    changePassword( scanner );
                    break;
                case 5:
                    System.out.println( "\nLogging out from Admin...\n" );
                    logout = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }

    }

    private static void showTAOperationsMenu ( final Scanner scanner, final Connection conn ) {
        boolean logout = false;

        while ( !logout ) {
            System.out.println( "\nTeaching Assistant Operations Menu:" );
            System.out.println( "1. Go to Active Course" );
            System.out.println( "2. View Courses" );
            System.out.println( "3. Change Password" );
            System.out.println( "4. Logout" );

            final int choice = getUserChoice( scanner, 4 );
            scanner.nextLine();
            switch ( choice ) {

                case 1:

                    goToActiveCourseTA( scanner, conn );
                    break;
                case 2:
                    viewCourses( scanner, conn );
                    break;
                case 3:
                    changePassword( scanner );
                    break;
                case 4:
                    System.out.println( "\nLogging out from Admin...\n" );
                    logout = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    public static void showStudentOperationsMenu ( final Scanner scanner, final Connection conn ) {
        System.out.println( "Fetching your enrolled courses and their content...\n" );

        final String querySQL = """
                    SELECT
                        c.CourseName AS course_name,
                        b.TextbookID AS textbook_id,
                        ch.ChapterID AS chapter_id,
                        ch.Title AS chapter_title,
                        s.SectionID AS section_id,
                        s.Title AS section_title,
                        b.block_id AS block_id,
                        b.type AS block_type
                    FROM Enrollment e
                    JOIN Course c ON e.course_id = c.course_id
                    JOIN Block b ON b.TextbookID = c.ETextbook
                    JOIN Chapter ch ON ch.TextbookID = b.TextbookID AND ch.ChapterID = b.chapter_id
                    JOIN Section s ON s.TextbookID = ch.TextbookID AND s.ChapterID = ch.ChapterID AND s.SectionID = b.section_id
                    WHERE e.student_id = ? AND e.Status = 'Enrolled'
                    ORDER BY c.CourseName, b.TextbookID, ch.ChapterID, s.SectionID, b.block_id
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, userID );

            try ( ResultSet rs = pstmt.executeQuery() ) {
                String currentTextbook = null;
                String currentChapter = null;
                String currentSection = null;

                while ( rs.next() ) {
                    final String textbookId = rs.getString( "textbook_id" );
                    final String chapterId = rs.getString( "chapter_id" );
                    final String chapterTitle = rs.getString( "chapter_title" );
                    final String sectionId = rs.getString( "section_id" );
                    final String sectionTitle = rs.getString( "section_title" );
                    final String blockId = rs.getString( "block_id" );
                    final String blockType = rs.getString( "block_type" );

                    if ( !textbookId.equals( currentTextbook ) ) {
                        currentTextbook = textbookId;
                        System.out.println( "E-book " + currentTextbook );
                    }

                    if ( !chapterId.equals( currentChapter ) ) {
                        currentChapter = chapterId;
                        System.out.println( "  Chapter " + chapterId + ": " + chapterTitle );
                    }

                    if ( !sectionId.equals( currentSection ) ) {
                        currentSection = sectionId;
                        System.out.println( "    Section " + sectionId + ": " + sectionTitle );
                    }

                    System.out.println( "      Block " + blockId + " (" + blockType + ")" );
                }

                if ( currentTextbook == null ) {
                    System.out.println( "No content found for your enrolled courses." );
                }
            }
        }
        catch ( final SQLException e ) {
            System.err.println( "Error fetching course content: " + e.getMessage() );
        }

        boolean logout = false;

        while ( !logout ) {
            // Display the menu options
            System.out.println( "\nStudent Landing:" );
            System.out.println( "1. View a section" );
            System.out.println( "2. View participation activity point" );
            System.out.println( "3. Logout" );

            final int choice = getUserChoice( scanner, 3 );
            switch ( choice ) {
                case 1:
                    viewSectionStudent( scanner, conn );
                    break;
                case 2:
                    viewParticipationPoint( scanner, conn );
                    break;
                case 3:
                    showMainMenu();
                    logout = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }

        }

    }
  
   private static void viewSectionStudent ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nView Section:" );

        // Step 1: Ask the user for section details
        System.out.print( "Enter E-textbook ID: " );
        final String eTextbookID = scanner.next();
        System.out.print( "Enter Chapter ID: " );
        final String chapterID = scanner.next();
        System.out.print( "Enter Section ID: " );
        final String sectionID = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            // Step 2: Display the menu
            System.out.println( "\n1. View Block" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    // Step 3: View the blocks in the selected section
                    viewBlockStudent( scanner, conn, eTextbookID, chapterID, sectionID );
                    break;

                case 2:
                    // Step 4: Go back to the landing page
                    System.out.println( "\nDiscarding input and going back to landing page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void viewBlockStudent ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID ) {
        System.out.println( "\nViewing blocks for Section:" );
        System.out.println( "E-textbook ID: " + eTextbookID );
        System.out.println( "Chapter ID: " + chapterID );
        System.out.println( "Section ID: " + sectionID );

        final String querySQL = """
                SELECT block_id, type, content
                FROM Block
                WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND (hidden = 'no' OR hidden IS NULL)
                ORDER BY block_id;
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, eTextbookID );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );

            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( !rs.isBeforeFirst() ) {
                    System.out.println( "No visible blocks found for this section." );
                    return;
                }

                // Iterate over the blocks
                while ( rs.next() ) {
                    final String blockID = rs.getString( "block_id" );
                    final String type = rs.getString( "type" );
                    final String content = rs.getString( "content" );

                    System.out.println( "\nBlock ID: " + blockID );
                    System.out.println( "Type: " + type );

                    if ( type.equalsIgnoreCase( "text" ) || type.equalsIgnoreCase( "picture" ) ) {
                        // Content block
                        System.out.println( "Content: " + content );
                        if ( !navigateBlock( scanner, "content", blockID, eTextbookID, chapterID, sectionID,
                                rs.isLast(), conn ) ) {
                            return;
                        }
                    }
                    else {
                        // Activity block
                        System.out.println( "Activity: " + content );
                        if ( !navigateBlock( scanner, "activity", blockID, eTextbookID, chapterID, sectionID,
                                rs.isLast(), conn ) ) {
                            return;
                        }
                    }
                }
            }
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }
    }

    private static boolean navigateBlock ( final Scanner scanner, final String blockType, final String blockID,
            final String eTextbookID, final String chapterID, final String sectionID, final boolean isLastBlock,
            final Connection conn ) {
        int totalPoints = 0;

        while ( true ) {
            System.out.println( "\n1. Next/Submit" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    if ( blockType.equalsIgnoreCase( "content" ) ) {
                        System.out.println( "Proceeding to the next block..." );
                    }
                    else if ( blockType.equalsIgnoreCase( "activity" ) ) {
                        // Query to get the question and answer
                        final String querySQL = """
                                SELECT question_text, option1, option2, option3, option4, answer
                                FROM Question
                                WHERE TextbookID = ? AND chapter_id = ? AND section_id = ? AND block_id = ?;
                                """;

                        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
                            pstmt.setString( 1, eTextbookID );
                            pstmt.setString( 2, chapterID );
                            pstmt.setString( 3, sectionID );
                            pstmt.setString( 4, blockID );

                            try ( ResultSet rs = pstmt.executeQuery() ) {
                                if ( rs.next() ) {
                                    final String questionText = rs.getString( "question_text" );
                                    final String option1 = rs.getString( "option1" );
                                    final String option2 = rs.getString( "option2" );
                                    final String option3 = rs.getString( "option3" );
                                    final String option4 = rs.getString( "option4" );
                                    final int correctAnswer = rs.getInt( "answer" );

                                    // Display the question and options
                                    System.out.println( "\nQuestion: " + questionText );
                                    System.out.println( "1. " + option1 );
                                    System.out.println( "2. " + option2 );
                                    System.out.println( "3. " + option3 );
                                    System.out.println( "4. " + option4 );

                                    // Prompt user for the answer
                                    System.out.print( "Enter the ID of the correct answer (1-4): " );
                                    final int userAnswer = scanner.nextInt();

                                    // Validate the answer
                                    if ( userAnswer == correctAnswer ) {
                                        System.out.println( "Correct! You've earned a point." );
                                        totalPoints++; // Increment total points
                                    }
                                    else {
                                        System.out.println( "Incorrect. The correct answer was: " + correctAnswer );
                                    }
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

                    if ( isLastBlock ) {
                        System.out.println( "No more blocks in this section. Returning to the main menu..." );
                        System.out.println( "Your total points: " + totalPoints ); // Display
                                                                                   // total
                                                                                   // points
                        showStudentOperationsMenu( scanner, conn ); // Redirect
                                                                    // to the
                                                                    // student
                                                                    // landing
                                                                    // page
                        return false; // Exit block navigation
                    }
                    return true; // Continue to the next block
                case 2:
                    System.out.println( "Returning to the previous menu..." );
                    return false; // Exit block navigation
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void viewParticipationPoint ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nView Participation Activity Points" );

        // SQL query to fetch participation points for the logged-in student
        final String querySQL = """
                SELECT total_participation_points, num_of_finished_activities
                FROM StudentCredit
                WHERE student_id = ?;
                """;

        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, userID );

            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    final int totalPoints = rs.getInt( "total_participation_points" );
                    final int finishedActivities = rs.getInt( "num_of_finished_activities" );

                    System.out.println( "Total Participation Points: " + totalPoints );
                    System.out.println( "Number of Finished Activities: " + finishedActivities );
                }
                else {
                    System.out.println( "No participation points found for this student." );
                }
            }
        }
        catch ( final SQLException e ) {
            System.err.println( "SQL Error: " + e.getMessage() );
        }

        // Display menu for navigation
        boolean goBack = false;
        while ( !goBack ) {
            System.out.println( "\n1. Go back" );

            final int choice = getUserChoice( scanner, 1 );

            if ( choice == 1 ) {
                System.out.println( "Returning to the previous menu..." );
                goBack = true;
            }
            else {
                System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }


    private static void goToActiveCourseFac ( final Scanner scanner, final Connection conn ) {
        System.out.print( "Enter Course ID: " );
        final String courseId = scanner.next(); // Capture course ID properly

        final String textbookId = findTextbookForCourse( conn, courseId ); // Lookup
                                                                           // textbook

        if ( textbookId == null ) {
            System.out.println( "No textbook found for Course ID: " + courseId );
            return; // Exit if no textbook is found
        }

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\nFaculty Active Course Menu for Course ID: " + courseId );
            System.out.println( "1. View Worklist" );
            System.out.println( "2. Approve Enrollment" );
            System.out.println( "3. View Students" );
            System.out.println( "4. Add New Chapter" );
            System.out.println( "5. Modify Chapters" );
            System.out.println( "6. Add TA" );
            System.out.println( "7. Go Back" );

            final int choice = getUserChoice( scanner, 7 );
            switch ( choice ) {
                case 1:
                    viewWorklist( scanner, conn, courseId );
                    break;
                case 2:
                    approveEnrollment( scanner, conn, courseId );
                    break;
                case 3:
                    viewStudents( scanner, conn, courseId );
                    break;
                case 4:
                    addNewChapterFac( scanner, conn, textbookId );
                    break;
                case 5:
                    modifyChapterFac( scanner, conn, textbookId );
                    break;
                case 6:
                    addTAToCourse( scanner, conn, courseId );
                    break;
                case 7:
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addNewChapterFac ( final Scanner scanner, final Connection conn, final String textbookID ) {
        System.out.println( "\nAdd New Chapter for Textbook ID: " + textbookID );

        // Prompt the user for Chapter ID and Title
        System.out.print( "Enter Unique Chapter ID: " );
        final String chapterID = scanner.next();
        scanner.nextLine(); // Consume newline
        System.out.print( "Enter Chapter Title: " );
        final String chapterTitle = scanner.nextLine();

        boolean goBack = false;
        // Add the new chapter to the database
        try {
            DBConnect.insertChapterData( conn, textbookID, chapterID, chapterTitle, null ); // No
            // "hidden"
            // column
            // value
            System.out.println( "New chapter added successfully." );
        }
        catch ( final SQLException e ) {
            System.out.println( "Error adding new chapter: " + e.getMessage() );
        }

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Add New Section" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 ); // Limit to 2
                                                            // options

            switch ( choice ) {
                case 1:

                    // Proceed to Add New Section (or implement logic to
                    // redirect)
                    addNewSectionFac( scanner, conn, textbookID, chapterID );
                    break;

                case 2:
                    // Discard the input and go back
                    System.out.println( "\nDiscarding input and going back to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    private static void addNewSectionFac ( final Scanner scanner, final Connection conn, final String textbookID,
            final String chapterID ) {
        System.out.println( "\nAdd New Section for Textbook ID: " + textbookID + ", Chapter ID: " + chapterID );

        // Prompt the user for Section Number and Title
        System.out.print( "Enter Section Number: " );
        final String sectionNumber = scanner.next();
        scanner.nextLine(); // Consume newline
        System.out.print( "Enter Section Title: " );
        final String sectionTitle = scanner.nextLine();

        // Add the new section to the database
        try {
            DBConnect.insertSectionData( conn, textbookID, chapterID, sectionNumber, sectionTitle, null ); // No
            // "hidden"
            // column
            // value
            System.out.println( "New section added successfully." );
        }
        catch ( final SQLException e ) {
            System.out.println( "Error adding new section: " + e.getMessage() );
        }
        boolean goBack = false;

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Add New Content Block" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 ); // Limit to 2
                                                            // options

            switch ( choice ) {
                case 1:

                    // Proceed to Add New Content Block (or implement logic to
                    // redirect)
                    addNewContentBlockFac( scanner, conn, textbookID, chapterID, sectionNumber );
                    break;

                case 2:
                    // Discard the input and go back
                    System.out.println( "\nDiscarding input and going back to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    private static void addNewContentBlockFac ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID ) {
        System.out.println( "\nAdd New Content Block:" );

        String contentBlockID = "";
        boolean validInput = false;

        while ( !validInput ) {
            System.out.print( "Enter Content Block ID (numeric): " );
            if ( scanner.hasNextInt() ) {
                final int blockNumInput = scanner.nextInt();
                contentBlockID = String.format( "block%02d", blockNumInput ); // Format
                                                                              // as
                                                                              // "block"
                                                                              // +
                                                                              // two-digit
                                                                              // number
                validInput = true;
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric content block ID." );

                scanner.next(); // Clear invalid input
            }
        }

        // Clear the newline character left after reading the block number
        scanner.nextLine();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add Text" );
            System.out.println( "2. Add Picture" );
            System.out.println( "3. Add Activity" );
            System.out.println( "4. Go Back" );

            final int choice = getUserChoice( scanner, 4 );

            switch ( choice ) {
                case 1:

                    addTextFac( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID );
                    break;
                case 2:
                    addPictureFac( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID );
                    break;
                case 3:
                    addActivityFac( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID );
                    break;
                case 4:
                    System.out.println( "\nReturning to previous page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    public static String findTextbookForCourse ( final Connection conn, final String courseId ) {
        final String querySQL = "SELECT ETextbook FROM Course WHERE course_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, courseId );

            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    return rs.getString( "ETextbook" ); // Return the ETextbook
                                                        // if found
                }
                else {
                    System.out.println( "No textbook found for Course ID: " + courseId );
                    return null;
                }
            }
        }
        catch ( final SQLException e ) {
            System.out.println( "Error retrieving textbook: " + e.getMessage() );
            return null;
        }
    }

    // Adds TA to the course
    private static void addTAToCourse ( final Scanner scanner, final Connection conn, final String courseID ) {
        System.out.print( "Enter First Name: " );
        final String firstName = scanner.next();
        System.out.print( "Enter Last Name: " );
        final String lastName = scanner.next();
        System.out.print( "Enter Email: " );
        final String email = scanner.next();
        System.out.print( "Enter Password: " );
        final String password = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\nMenu:" );
            System.out.println( "1. Save" );
            System.out.println( "3. Cancel" );

            final int choice = getUserChoice( scanner, 3 );
            switch ( choice ) {

                case 1:
                    try {
                        DBConnect.insertTeachingAssistantData( conn, firstName, lastName, email, password, courseID,
                                userID );
                    }
                    catch ( final SQLException e ) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void viewWorklist ( final Scanner scanner, final Connection conn, final String courseID ) {
        // Query to get pending students for the given course ID
        final String querySQL = "SELECT student_id FROM Enrollment WHERE course_id = ? AND Status = 'Pending'";

        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, courseID );

            try ( ResultSet rs = pstmt.executeQuery() ) {
                System.out.println( "Waiting list of students for Course ID: " + courseID );

                // Check if there are any pending students
                if ( !rs.isBeforeFirst() ) {
                    System.out.println( "No students in the waiting list." );
                }
                else {
                    while ( rs.next() ) {
                        final String studentId = rs.getString( "student_id" );
                        System.out.println( "Student ID: " + studentId );
                    }
                }
            }
        }
        catch ( final SQLException e ) {
            System.out.println( "Error retrieving the waiting list: " + e.getMessage() );
        }

        // Menu option to go back
        System.out.println( "1. Go back" );
        final int choice = getUserChoice( scanner, 1 );
        if ( choice == 1 ) {
            System.out.println( "Returning to the previous menu." );
        }
    }

    private static void approveEnrollment ( final Scanner scanner, final Connection conn, final String courseID ) {
        System.out.println( "\nApprove Enrollment for Course ID: " + courseID );

        // Ask for the Student ID
        System.out.print( "Enter Student ID to approve enrollment: " );
        final String studentID = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            // Display the menu
            System.out.println( "\n1. Save" );
            System.out.println( "2. Cancel" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    try {
                        // Step 1: Check the current number of enrolled students
                        // and course capacity
                        final String capacityCheckSQL = """
                                SELECT COUNT(*) AS enrolled_count, c.CourseCapacity
                                FROM Enrollment e
                                JOIN Course c ON e.course_id = c.course_id
                                WHERE e.course_id = ? AND e.Status = 'Enrolled'
                                GROUP BY c.CourseCapacity
                                """;

                        try ( PreparedStatement pstmt = conn.prepareStatement( capacityCheckSQL ) ) {
                            pstmt.setString( 1, courseID );

                            try ( ResultSet rs = pstmt.executeQuery() ) {
                                if ( rs.next() ) {
                                    final int enrolledCount = rs.getInt( "enrolled_count" );
                                    final int courseCapacity = rs.getInt( "CourseCapacity" );

                                    // Step 2: Check if there's room for
                                    // enrollment
                                    if ( enrolledCount < courseCapacity ) {
                                        // Room available, enroll the student
                                        updateEnrollmentStatus( conn, courseID, studentID, "Enrolled" );
                                        System.out.println( "\nSuccess: Student ID " + studentID
                                                + " has been enrolled in Course ID " + courseID + "." );
                                    }
                                    else {
                                        // No room, set to Pending
                                        updateEnrollmentStatus( conn, courseID, studentID, "Pending" );
                                        System.out.println( "\nNotice: No room available. Student ID " + studentID
                                                + " has been added to the waiting list (Pending) for Course ID "
                                                + courseID + "." );
                                    }
                                }
                                else {
                                    System.out.println( "\nFailure: Course ID " + courseID
                                            + " does not exist or no capacity information found." );
                                }
                            }
                        }
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error checking enrollment capacity: " + e.getMessage() );
                    }

                    // Return to the previous menu after saving
                    break;

                case 2:
                    // Discard the input and return to the previous menu
                    System.out.println( "\nCanceling approval process and returning to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    // Helper method to update the student's enrollment status
    private static void updateEnrollmentStatus ( final Connection conn, final String courseID, final String studentID,
            final String status ) throws SQLException {
        final String updateSQL = "UPDATE Enrollment SET Status = ? WHERE course_id = ? AND student_id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setString( 1, status );
            pstmt.setString( 2, courseID );
            pstmt.setString( 3, studentID );
            final int rowsAffected = pstmt.executeUpdate();

            if ( rowsAffected == 0 ) {
                System.out.println( "\nFailure: No pending enrollment found for Student ID " + studentID
                        + " in Course ID " + courseID + "." );
            }
        }
    }

    private static void goToEvaluationCourse ( final Scanner scanner, final Connection conn ) {

        // Step 1: Ask the user for the course ID
        System.out.println( "Enter the Course ID for the evaluation course:" );
        final String courseId = scanner.nextLine();
        final String textbookID = findTextbookForCourse( conn, courseId );
        // Validate the course ID

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\nFaculty Evaluation Course Menu:" );
            System.out.println( "1. Add New Chapter" );
            System.out.println( "2. Modify Chapters" );
            System.out.println( "3. Go Back" );

            final int choice = getUserChoice( scanner, 3 );
            switch ( choice ) {

                case 1:
                    addNewChapterFac( scanner, conn, textbookID );
                    break;
                case 2:
                    modifyChapterFac( scanner, conn, textbookID );
                    break;
                case 3:
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void goToActiveCourseTA ( final Scanner scanner, final Connection conn ) {

        // Step 1: Ask the user for the course ID
        System.out.println( "Enter the Course ID for the active course:" );
        final String courseId = scanner.nextLine();

        // Validate the course ID
        final String textbookID = findTextbookForCourse( conn, courseId );

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\nTeaching Assistant Active Course Menu:" );
            System.out.println( "1. View Students" );
            System.out.println( "2. Add Chapter" );
            System.out.println( "3. Modify Chapters" );
            System.out.println( "4. Go Back" );

            final int choice = getUserChoice( scanner, 4 );
            switch ( choice ) {

                case 1:
                    viewStudents( scanner, conn, courseId );
                    break;
                case 2:
                    addNewChapterTA( scanner, conn, textbookID );
                    break;
                case 3:
                    modifyChapterTA( scanner, conn, textbookID );
                    break;
                case 4:
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addNewChapterTA ( final Scanner scanner, final Connection conn, final String textbookID ) {
        System.out.println( "\nAdd New Chapter for Textbook ID: " + textbookID );

        // Prompt the user for Chapter ID and Title
        System.out.print( "Enter Unique Chapter ID: " );
        final String chapterID = scanner.next();
        scanner.nextLine(); // Consume newline
        System.out.print( "Enter Chapter Title: " );
        final String chapterTitle = scanner.nextLine();

        boolean goBack = false;
        // Add the new chapter to the database
        try {
            DBConnect.insertChapterData( conn, textbookID, chapterID, chapterTitle, null ); // No
            // "hidden"
            // column
            // value
            System.out.println( "New chapter added successfully." );
        }
        catch ( final SQLException e ) {
            System.out.println( "Error adding new chapter: " + e.getMessage() );
        }

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Add New Section" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 ); // Limit to 2
                                                            // options

            switch ( choice ) {
                case 1:

                    // Proceed to Add New Section
                    addNewSectionTA( scanner, conn, textbookID, chapterID );
                    break;

                case 2:
                    // Discard the input and go back
                    System.out.println( "\nDiscarding input and going back to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    private static void addNewSectionTA ( final Scanner scanner, final Connection conn, final String textbookID,
            final String chapterID ) {
        System.out.println( "\nAdd New Section for Textbook ID: " + textbookID + ", Chapter ID: " + chapterID );

        // Prompt the user for Section Number and Title
        System.out.print( "Enter Section Number: " );
        final String sectionNumber = scanner.next();
        scanner.nextLine(); // Consume newline
        System.out.print( "Enter Section Title: " );
        final String sectionTitle = scanner.nextLine();

        // Add the new section to the database
        try {
            DBConnect.insertSectionData( conn, textbookID, chapterID, sectionNumber, sectionTitle, null ); // No
            // "hidden"
            // column
            // value
            System.out.println( "New section added successfully." );
        }
        catch ( final SQLException e ) {
            System.out.println( "Error adding new section: " + e.getMessage() );
        }
        boolean goBack = false;

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Add New Content Block" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 ); // Limit to 2
                                                            // options

            switch ( choice ) {
                case 1:

                    // Proceed to Add New Content Block (or implement logic to
                    // redirect)
                    addNewContentBlockTA( scanner, conn, textbookID, chapterID, sectionNumber );
                    break;

                case 2:
                    // Discard the input and go back
                    System.out.println( "\nDiscarding input and going back to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    private static void addNewContentBlockTA ( final Scanner scanner, final Connection conn, final String textbookID,
            final String chapterID, final String sectionID ) {
        System.out.println( "\nAdd New Content Block for Textbook ID: " + textbookID + ", Chapter ID: " + chapterID
                + ", Section ID: " + sectionID );

        // Prompt the user for Content Block ID
        System.out.print( "Enter Content Block ID: " );
        final String contentBlockID = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Add Text" );
            System.out.println( "2. Add Picture" );
            System.out.println( "3. Add Activity" );
            System.out.println( "4. Hide Activity" );
            System.out.println( "5. Go Back" );

            final int choice = getUserChoice( scanner, 5 ); // Limit to 5
                                                            // options

            switch ( choice ) {
                case 1:

                    addTextTA( scanner, conn, textbookID, chapterID, sectionID, contentBlockID );
                    break;
                case 2:
                    addPictureTA( scanner, conn, textbookID, chapterID, sectionID, contentBlockID );
                    break;
                case 3:
                    addActivityTA( scanner, conn, textbookID, chapterID, sectionID, contentBlockID );
                    break;
                case 4:
                    try {
                        // Hide Activity
                        System.out.print( "Enter Unique Activity ID to Hide: " );
                        final String uniqueActivityID = scanner.next();

                        DBConnect.hideActivity( conn, textbookID, chapterID, sectionID, contentBlockID,
                                uniqueActivityID );
                        System.out.println( "Activity successfully hidden." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error hiding activity: " + e.getMessage() );
                    }
                    break;
                case 5:
                	// Discard the input and go back
                    System.out.println( "\nDiscarding input and going back to the previous menu." );
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addTextTA ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID ) {
        scanner.nextLine();
        System.out.println( "\nAdd Text:" );

        // Prompt for multi-word text content
        System.out.print( "Enter Text Content: " );
        final String textContent = scanner.nextLine(); // Read the entire line
                                                       // for multi-word input

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:

                    try {
                        DBConnect.insertBlockData( conn, eTextbookID, chapterID, sectionID, contentBlockID, "text",
                                textContent, null, permission );
                    }
                    catch ( final SQLException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println( "\nText added successfully:" );
                    System.out.println( "Content: " + textContent );
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back to previous page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addPictureTA ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID ) {
        System.out.println( "\nAdd Picture:" );
        System.out.print( "Enter Picture Information: " );
        final String pictureInfo = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    try {
                        DBConnect.insertBlockData( conn, eTextbookID, chapterID, sectionID, contentBlockID, "picture",
                                pictureInfo, null, permission );
                    }
                    catch ( final SQLException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println( "\nPicture added successfully:" );
                    System.out.println( "Picture Information: " + pictureInfo );
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back to previous page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addActivityTA ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID ) {
        System.out.println( "\nAdd Activity:" );

        String activityID = "";
        boolean validInput = false;

        // Loop until a valid numeric activity ID is entered

        while ( !validInput ) {
            System.out.print( "Enter Unique Activity ID (numeric): " );
            if ( scanner.hasNextInt() ) {
                final int activityNumInput = scanner.nextInt();
                activityID = String.format( "ACT%02d", activityNumInput ); // Format
                                                                           // as
                                                                           // "ACT"
                                                                           // +
                                                                           // two-digit
                                                                           // number
                validInput = true;
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric activity ID." );

                scanner.next(); // Clear invalid input
            }
        }

        try {
            DBConnect.insertBlockData( conn, eTextbookID, chapterID, sectionID, contentBlockID, "activity", activityID,
                    null, permission );

            DBConnect.insertActivityData( conn, eTextbookID, chapterID, sectionID, contentBlockID, activityID, null, permission );
        }
        catch ( final SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Clear the newline character left after reading the activity number
        scanner.nextLine();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add Question" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    addQuestionTA( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID, activityID ); 
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back to previous page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addQuestionTA ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID, final String activityID ) {
        System.out.println( "\nAdd Question:" );

        String questionID = "";
        boolean validInput = false;

        // Loop until a valid numeric question ID is entered

        while ( !validInput ) {
            System.out.print( "Enter Question ID (numeric): " );
            if ( scanner.hasNextInt() ) {
                final int questionNumInput = scanner.nextInt();
                questionID = "Q" + questionNumInput; // Format as "Q" + the
                                                     // number
                validInput = true;
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric question ID." );

                scanner.next(); // Clear invalid input
            }
        }

        // Clear the newline character left after reading the question ID
        scanner.nextLine();

        // Input question text

        System.out.print( "Enter Question Text: " );

        final String questionText = scanner.nextLine();

        // Input options (Text, Explanation, Label)
        final String[] optionTexts = new String[4];
        final String[] optionExplanations = new String[4];
        final String[] optionLabels = new String[4];
        int answer = -1;

        for ( int i = 0; i < 4; i++ ) {
            System.out.print( "Enter Option " + ( i + 1 ) + " Text: " );
            optionTexts[i] = scanner.nextLine();

            System.out.print( "Enter Option " + ( i + 1 ) + " Explanation: " );
            optionExplanations[i] = scanner.nextLine();

            System.out.print( "Enter Option " + ( i + 1 ) + " Label (Correct or Incorrect): " );
            optionLabels[i] = scanner.next();
            scanner.nextLine(); // Consume newline

            // Check if the option is labeled as "Correct" and set answer to the
            // index
            if ( optionLabels[i].equalsIgnoreCase( "Correct" ) ) {

                answer = i + 1; // Store the correct answer index (1-based)
            }
        }

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Save" );
            System.out.println( "2. Cancel" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:

                    // Ensure answer is set to a valid option if marked as
                    // "Correct"
                    if ( answer == -1 ) {
                        System.out.println( "Error: No correct answer marked. Please try again." );
                    }
                    else {
                        try {
                            DBConnect.insertQuestionData( conn, eTextbookID, chapterID, sectionID, contentBlockID,
                                    activityID, questionID, questionText, optionTexts[0], optionExplanations[0],
                                    optionTexts[1], optionExplanations[1], optionTexts[2], optionExplanations[2],
                                    optionTexts[3], optionExplanations[3], answer );
                        }
                        catch ( final SQLException e ) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println( "\nQuestion saved successfully:" );
                        System.out.println( "Question ID: " + questionID );
                        System.out.println( "Question Text: " + questionText );

                        for ( int i = 0; i < 4; i++ ) {
                            System.out.println( "Option " + ( i + 1 ) + " Text: " + optionTexts[i] );
                            System.out.println( "Option " + ( i + 1 ) + " Explanation: " + optionExplanations[i] );
                            System.out.println( "Option " + ( i + 1 ) + " Label: " + optionLabels[i] );
                        }

                        // Redirect back to the add activity page
                        addActivityTA( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID );

                    }
                    break;

                case 2:
                    System.out.println( "\nDiscarding input and going back to previous page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void modifyChapterTA ( final Scanner scanner, final Connection conn, final String textbookID ) {
        System.out.println( "\nModify Chapter for Textbook ID: " + textbookID );

        // Prompt for Unique Chapter ID
        System.out.print( "Enter Unique Chapter ID: " );
        final String chapterID = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Add New Section" );
            System.out.println( "2. Modify Section" );
            System.out.println( "3. Go Back" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:
                    // Add New Section
                    addNewSectionTA( scanner, conn, textbookID, chapterID );
                    break;

                case 2:
                    // Modify Section
                    modifySectionTA( scanner, conn, textbookID, chapterID );
                    break;

                case 3:
                    // Go Back
                    System.out.println( "\nReturning to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    private static void modifySectionTA ( final Scanner scanner, final Connection conn, final String textbookID,
            final String chapterID ) {
        System.out.println( "\nModify Section for Chapter ID: " + chapterID + " in Textbook ID: " + textbookID );

        // Prompt for Section Details
        System.out.print( "Enter Section Number: " );
        final String sectionID = scanner.next();
        System.out.print( "Enter Section Title: " );
        scanner.nextLine(); // Consume newline
        final String sectionTitle = scanner.nextLine();

        boolean goBack = false;

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Add New Content Block" );
            System.out.println( "2. Modify Content Block" );
            System.out.println( "3. Delete Content Block" );
            System.out.println( "4. Hide Content Block" );
            System.out.println( "5. Go Back" );

            final int choice = getUserChoice( scanner, 5 );

            switch ( choice ) {
                case 1:
                    // Add New Content Block

                    addNewContentBlockTA( scanner, conn, textbookID, chapterID, sectionID );
                    break;

                case 2:

                    modifyContentBlockTA( scanner, conn, textbookID, chapterID );
                    break;

                case 3:
                    System.out.print( "Enter Unique Block ID: " );
                    scanner.nextLine(); // Consume newline
                    final String blockID = scanner.nextLine();
                    // Modify Content Block
                    // Delete Content Block
                    try {
                        DBConnect.deleteContentBlock( conn, textbookID, chapterID, sectionID, blockID );
                    }
                    catch ( final SQLException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;

                case 4:
                    System.out.print( "Enter Unique Block ID: " );
                    scanner.nextLine(); // Consume newline
                    final String contentBlockID = scanner.nextLine();
                    // Hide Content Block

                    try {
                        DBConnect.hideContentBlock( conn, textbookID, chapterID, sectionID, contentBlockID );
                    }
                    catch ( final SQLException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;

                case 5:
                    // Go Back
                    System.out.println( "\nReturning to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    private static void modifyContentBlockTA ( final Scanner scanner, final Connection conn, final String textbookID,
            final String chapterID ) {

    }

    private static void viewStudents ( final Scanner scanner, final Connection conn, final String courseID ) {
        System.out.println( "\nList of Enrolled Students for Course ID: " + courseID );

        // SQL query to retrieve students with Status = 'Enrolled'
        final String querySQL = "SELECT student_id FROM Enrollment WHERE course_id = ? AND Status = 'Enrolled'";

        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, courseID );

            try ( ResultSet rs = pstmt.executeQuery() ) {
                // Check if there are enrolled students
                if ( !rs.isBeforeFirst() ) {
                    System.out.println( "No students are currently enrolled in this course." );
                }
                else {
                    System.out.println( "Enrolled Students:" );
                    while ( rs.next() ) {
                        final String studentID = rs.getString( "student_id" );
                        System.out.println( "- " + studentID );
                    }
                }
            }
        }
        catch ( final SQLException e ) {
            System.out.println( "Error retrieving enrolled students: " + e.getMessage() );
        }

        // Display the menu
        System.out.println( "\n1. Go back" );
        final int choice = getUserChoice( scanner, 1 );
        if ( choice == 1 ) {
            System.out.println( "Returning to the previous menu." );
        }
    }

    private static void viewCourses ( final Scanner scanner, final Connection conn ) {
        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\nCourse Display:" );
            System.out.println( "1. Display All Courses" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );
            switch ( choice ) {
                case 1:
                    DBConnect.displayAllCourses( conn );
                    break;
                case 2:
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void changePassword ( final Scanner scanner ) {
        final boolean goBack = false;

        // while ( !goBack ) {
        // System.out.println( "\nPassword Settings:" );
        // System.out.println( "1. Update" );
        // System.out.println( "2. Go Back" );
        //
        // final int choice = getUserChoice( scanner, 2 );
        // switch ( choice ) {
        //
        // case 1:
        // // if current pass != currPass FAIL
        //
        // if ( newPass != confPass ) {
        // System.out.println( "Fail" );
        // }
        // else {
        // System.out.println( "Success" );
        // // change password
        // }
        // // TODO
        // case 2:
        // goBack = true;
        // break;
        // default:
        // System.out.println( "Invalid choice. Please enter a valid option." );
        // }
        // }
    }

    private static void createFacultyAccount ( final Scanner scanner, final Connection conn ) {

        System.out.println( "\nCreate Faculty Account:" );
        // Prompt for valid first name
        String firstName;
        while ( true ) {
            System.out.print( "Enter First Name (at least 2 characters): " );
            firstName = scanner.next();
            if ( firstName.length() >= 2 ) {
                break;
            }
            System.out.println( "Invalid input. First name must be at least 2 characters." );
        }

        // Prompt for valid last name
        String lastName;
        while ( true ) {
            System.out.print( "Enter Last Name (at least 2 characters): " );
            lastName = scanner.next();
            if ( lastName.length() >= 2 ) {
                break;
            }
        }

        // Prompt for valid email
        String email;

        while ( true ) {
            System.out.print( "Enter E-mail: " );
            email = scanner.next();
            if ( email.contains( "@" ) && email.contains( "." ) ) { // Simple
                                                                    // email
                                                                    // validation
                break;
            }
            System.out.println( "Invalid input. Please enter a valid email address with @ and .com" );

        }

        // Prompt for valid password
        String password;

        while ( true ) {
            System.out.print( "Enter Password: " );
            password = scanner.next();
            if ( password.length() >= 2 ) {
                break;
            }
            System.out.println( "Invalid input. Password must be at least 2 characters." );
        }

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add User" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    try {
                        // Call the method to insert faculty details into the
                        // database
                        DBConnect.insertFacultyDetails( conn, firstName, lastName, email, password );
                        System.out.println( "\nUser added successfully:" );
                        System.out.println( "Name: " + firstName + " " + lastName );
                        System.out.println( "Email: " + email );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error adding user: " + e.getMessage() );
                    }
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back...\n" );
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void createETextbook ( final Scanner scanner, final Connection conn ) {
        try {

            System.out.println( "\nCreate E-textbook:" );

            // Consume any leftover newline character
            scanner.nextLine();

            System.out.print( "Enter E-textbook Title: " );

            final String title = scanner.nextLine(); // Read the full line for
                                                     // title

            System.out.print( "Enter Unique E-textbook ID: " );
            while ( !scanner.hasNextInt() ) { // Ensure correct input for
                                              // integer
                System.out.print( "Invalid input. Please enter a numeric E-textbook ID: " );
                scanner.next(); // Discard non-integer input
            }
            final String eTextbookID = scanner.next();
            scanner.nextLine(); // Clear newline character from the buffer

            // Insert data into the table
            DBConnect.insertETextbookData( conn, eTextbookID, title );

            boolean goBack = false;

            while ( !goBack ) {
                System.out.println( "\n1. Add New Chapter" );
                System.out.println( "2. Go Back" );

                final int choice = getUserChoice( scanner, 2 );

                switch ( choice ) {
                    case 1:
                        addNewChapterAdmin( scanner, conn, eTextbookID );
                        break;
                    case 2:
                        System.out.println( "\nDiscarding input and going back...\n" );
                        goBack = true;
                        break;
                    default:
                        System.out.println( "Invalid choice. Please enter a valid option." );
                }
            }
        }
        catch ( final SQLException e ) {
            System.out.println( "An error occurred while creating the E-textbook: " + e.getMessage() );
        }
    }

    private static void modifyETextbook ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nModify E-textbook:" );
        System.out.print( "Enter Unique E-textbook ID: " );
        final String eTextbookID = scanner.next();

        // need to find courseId for the textbook
        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add New Chapter" );
            System.out.println( "2. Modify Chapter" );
            System.out.println( "3. Go Back" );
            System.out.println( "4. Landing Page" );

            final int choice = getUserChoice( scanner, 4 );

            switch ( choice ) {
                case 1:
                    addNewChapterAdmin( scanner, conn, eTextbookID );
                    break;
                case 2:
                    modifyChapterAdmin( scanner, conn );
                    break;
                case 3:
                    System.out.println( "\nDiscarding input and going back to Admin Home Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;
                case 4:
                    System.out.println( "\nDiscarding input and returning to previous page...\n" );
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void createNewActiveCourse ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nCreate New Active Course:" );

        // Input course details
        System.out.print( "Enter Unique Course ID: " );
        final String courseID = scanner.next();
        System.out.print( "Enter Course Name: " );
        scanner.nextLine(); // Consume newline
        final String courseName = scanner.nextLine();
        System.out.print( "Enter Unique ID of the E-textbook: " );
        final String eTextbookID = scanner.next();
        System.out.print( "Enter Faculty Member ID: " );
        final String facultyID = scanner.next();
        System.out.print( "Enter Course Start Date (YYYY-MM-DD): " );
        final String startDate = scanner.next();
        System.out.print( "Enter Course End Date (YYYY-MM-DD): " );
        final String endDate = scanner.next();
        System.out.print( "Enter Unique Token: " );
        final String uniqueToken = scanner.next();
        System.out.print( "Enter Course Capacity: " );
        final int capacity = scanner.nextInt();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Save" );
            System.out.println( "2. Cancel" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:
                    try {
                        // Convert string dates to SQL Date
                        final Date sqlStartDate = Date.valueOf( startDate );
                        final Date sqlEndDate = Date.valueOf( endDate );

                        // Save course data to the database (TA ID is null)
                        DBConnect.insertCourseData( conn, courseID, courseName, eTextbookID, "Active", facultyID, null,
                                sqlStartDate, sqlEndDate, uniqueToken, capacity );

                        System.out.println( "\nCourse saved successfully!" );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error saving course: " + e.getMessage() );
                    }
                    catch ( final IllegalArgumentException e ) {
                        System.out.println( "Invalid date format. Please use YYYY-MM-DD." );
                    }
                    break;

                case 2:
                    System.out.println( "\nDiscarding input and going back to the previous page...\n" );
                    goBack = true; // Return to the previous page without saving
                    break;

                case 3:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void createNewEvaluationCourse ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nCreate New Evaluation Course:" );

        // Input course details
        System.out.print( "Enter Unique Course ID: " );
        final String courseID = scanner.next();
        System.out.print( "Enter Course Name: " );
        scanner.nextLine(); // Consume newline
        final String courseName = scanner.nextLine();
        System.out.print( "Enter Unique ID of the E-textbook: " );
        final String eTextbookID = scanner.next();
        System.out.print( "Enter Faculty Member ID: " );
        final String facultyID = scanner.next();
        System.out.print( "Enter Course Start Date (YYYY-MM-DD): " );
        final String startDate = scanner.next();
        System.out.print( "Enter Course End Date (YYYY-MM-DD): " );
        final String endDate = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Save" );
            System.out.println( "2. Cancel" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:
                    try {
                        // Convert string dates to SQL Date
                        final Date sqlStartDate = Date.valueOf( startDate );
                        final Date sqlEndDate = Date.valueOf( endDate );

                        // Save course data to the database (Evaluation courses
                        // have no TA ID)
                        DBConnect.insertCourseData( conn, courseID, courseName, eTextbookID, "Evaluation", facultyID,
                                null, sqlStartDate, sqlEndDate, null, 0 );

                        System.out.println( "\nEvaluation Course saved successfully!" );
                        System.out.println( "Course ID: " + courseID );
                        System.out.println( "Course Name: " + courseName );
                        System.out.println( "E-textbook ID: " + eTextbookID );
                        System.out.println( "Faculty Member ID: " + facultyID );
                        System.out.println( "Start Date: " + startDate );
                        System.out.println( "End Date: " + endDate );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error saving course: " + e.getMessage() );
                    }
                    catch ( final IllegalArgumentException e ) {
                        System.out.println( "Invalid date format. Please use YYYY-MM-DD." );
                    }
                    break;

                case 2:
                    System.out.println( "\nDiscarding input and going back to the previous page...\n" );
                    goBack = true; // Return to the previous page without saving
                    break;

                case 3:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn ); 
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void modifyChapterAdmin ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nModify Chapter:" );
        System.out.print( "Enter Unique Chapter ID: " );
        final String chapterID = scanner.next();
        final String textbookId = findTextbookIdByChapterId( conn, chapterID );
        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add New Section" );
            System.out.println( "2. Modify Section" );
            System.out.println( "3. Go Back" );
            System.out.println( "4. Landing Page" );

            final int choice = getUserChoice( scanner, 4 );

            switch ( choice ) {
                case 1:
                    addNewSectionAdmin( scanner, conn, textbookId, chapterID );
                    break;
                case 2:
                    modifySectionAdmin( scanner, conn );
                    break;
                case 3:
                    System.out.println( "\nDiscarding input and going back to Modify E-textbook page...\n" );
                    modifyETextbook( scanner, conn );
                    break;
                case 4:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    public static String findTextbookIdByChapterId ( final Connection conn, final String chapterId ) {
        final String querySQL = "SELECT TextbookID FROM Chapter WHERE ChapterID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, chapterId );

            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    return rs.getString( "TextbookID" ); // Return TextbookID as
                                                         // a String
                }
                else {
                    System.out.println( "No matching TextbookID found for ChapterID: " + chapterId );
                    return null;
                }
            }
        }
        catch ( final SQLException e ) {
            System.out.println( "Error retrieving TextbookID: " + e.getMessage() );
            return null;
        }
    }

    private static void modifySectionAdmin ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nModify Section:" );
        System.out.print( "Enter Unique E-textbook ID: " );
        final String eTextbookID = scanner.next();
        System.out.print( "Enter Chapter ID: " );
        final String chapterID = scanner.next();
        System.out.print( "Enter Section Number: " );
        final String sectionNumber = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add New Content Block" );
            System.out.println( "2. Modify Content Block" );
            System.out.println( "3. Go Back" );
            System.out.println( "4. Landing Page" );

            final int choice = getUserChoice( scanner, 4 );

            switch ( choice ) {
                case 1:
                    addNewContentBlockAdmin( scanner, conn, eTextbookID, chapterID, sectionNumber );
                    break;
                case 2:
                    modifyContentBlockAdmin( scanner, conn, eTextbookID, chapterID, sectionNumber );
                    break;
                case 3:
                    System.out.println( "\nDiscarding input and going back to Modify Chapter page...\n" );
                    goBack = true;
                    break;
                case 4:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void modifyContentBlockAdmin ( final Scanner scanner, final Connection conn, final String textbookId,
            final String chapterId, final String sectionId ) {
        System.out.println( "\nModify Content Block:" );
        System.out.print( "Enter Content Block ID: " );
        final String contentBlockID = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add Text" );
            System.out.println( "2. Add Picture" );
            System.out.println( "3. Add New Activity" );
            System.out.println( "4. Go Back" );
            System.out.println( "5. Landing Page" );

            final int choice = getUserChoice( scanner, 5 );

            switch ( choice ) {
                case 1:
                    addTextAdmin( scanner, conn, textbookId, chapterId, sectionId, contentBlockID );
                    break;
                case 2:
                    addPictureAdmin( scanner, conn, textbookId, chapterId, sectionId, contentBlockID );
                    break;
                case 3:
                    addActivityAdmin( scanner, conn, textbookId, chapterId, sectionId, contentBlockID );
                    break;
                case 4:
                    System.out.println( "\nDiscarding input and going back to previous page...\n" );
                    goBack = true;
                    break;
                case 5:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void modifyContentBlockFac ( final Scanner scanner, final Connection conn, final String textbookID,
            final String chapterID, final String sectionID ) {
        System.out.println( "\nModify Content Block for Textbook ID: " + textbookID + ", Chapter ID: " + chapterID
                + ", Section ID: " + sectionID );

        // Prompt for Content Block ID
        System.out.print( "Enter Content Block ID: " );
        final String contentBlockID = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Hide Content Block" );
            System.out.println( "2. Delete Content Block" );
            System.out.println( "3. Add Text" );
            System.out.println( "4. Add Picture" );
            System.out.println( "5. Hide Activity" );
            System.out.println( "6. Delete Activity" );
            System.out.println( "7. Add Activity" );
            System.out.println( "8. Go Back" );

            final int choice = getUserChoice( scanner, 8 );

            switch ( choice ) {
                case 1:
                    // Hide Content Block
                    try {
                        DBConnect.hideContentBlock( conn, textbookID, chapterID, sectionID, contentBlockID );
                        System.out.println( "Content block successfully hidden." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error hiding content block: " + e.getMessage() );
                    }
                    break;

                case 2:
                    // Delete Content Block
                    try {
                        DBConnect.deleteContentBlock( conn, textbookID, chapterID, sectionID, contentBlockID );
                        System.out.println( "Content block successfully deleted." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error deleting content block: " + e.getMessage() );
                    }
                    break;

                case 3:
                    // Add Text to Content Block
                    System.out.print( "Enter Text Content: " );
                    scanner.nextLine(); // Consume newline
                    final String textContent = scanner.nextLine();
                    try {
                        DBConnect.updateBlockContent( conn, textbookID, chapterID, sectionID, contentBlockID, "Text",
                                textContent );
                        System.out.println( "Text added to content block successfully." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error adding text: " + e.getMessage() );
                    }
                    break;

                case 4:
                    // Add Picture to Content Block
                    System.out.print( "Enter Picture Content (URL or Description): " );
                    scanner.nextLine(); // Consume newline
                    final String pictureContent = scanner.nextLine();
                    try {
                        DBConnect.updateBlockContent( conn, textbookID, chapterID, sectionID, contentBlockID, "Picture",
                                pictureContent );
                        System.out.println( "Picture added to content block successfully." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error adding picture: " + e.getMessage() );
                    }
                    break;

                case 5:
                    // Hide Activity
                    System.out.print( "Enter Unique Activity ID to Hide: " );
                    final String uniqueActivityID = scanner.next();
                    try {
                        DBConnect.hideActivity( conn, textbookID, chapterID, sectionID, contentBlockID,
                                uniqueActivityID );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error hiding activity: " + e.getMessage() );
                    }
                    break;

                case 6:
                    // Delete Activity
                    System.out.print( "Enter Unique Activity ID to Delete: " );
                    final String activityIDToDelete = scanner.next();
                    try {
                        DBConnect.deleteActivity( conn, textbookID, chapterID, sectionID, contentBlockID,
                                activityIDToDelete );
                        System.out.println( "Activity successfully deleted." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error deleting activity: " + e.getMessage() );
                    }
                    break;

                case 7:
                    // Add Activity
                    System.out.print( "Enter Unique Activity ID: " );
                    final String uniqueActivityIDToAdd = scanner.next();
                    System.out.print( "Enter Activity Details: " );
                    scanner.nextLine(); // Consume newline
                    final String activityContent = scanner.nextLine();
                    try {
                        DBConnect.insertActivityString( conn, textbookID, chapterID, sectionID, contentBlockID,
                                uniqueActivityIDToAdd, activityContent );
                        System.out.println( "Activity added successfully." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error adding activity: " + e.getMessage() );
                    }
                    break;

                case 8:
                    // Go Back
                    System.out.println( "\nReturning to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    private static void addNewChapterAdmin ( final Scanner scanner, final Connection conn, final String eTextbookID ) {
        System.out.println( "\nAdd New Chapter:" );

        String chapterID = "";
        boolean validInput = false;

        // Loop until a valid chapter number is entered
        while ( !validInput ) {
            System.out.print( "Enter the chapter number (numeric): " );
            if ( scanner.hasNextInt() ) {
                // If input is a valid integer, create the chapter ID
                final int chapterNumber = scanner.nextInt();
                chapterID = String.format( "chap%02d", chapterNumber ); // Format
                                                                        // as
                                                                        // "chap"
                                                                        // +
                                                                        // two-digit
                                                                        // number
                validInput = true;
                scanner.nextLine(); // Clear the newline character left by
                                    // nextInt()
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric chapter number." );
                scanner.next(); // Clear invalid input
            }
        }

        // Prompt for the chapter title
        System.out.print( "Enter Chapter Title: " );
        final String chapterTitle = scanner.nextLine(); // Read the entire line
                                                        // for the title

        // Add the chapter to the database
        try {
            DBConnect.insertChapterData( conn, eTextbookID, chapterID, chapterTitle, null );
            System.out.println( "Chapter added successfully." );
        }
        catch ( final SQLException e ) {
            e.printStackTrace();
        }

        // Allow further operations after adding the chapter
        boolean goBack = false;
        while ( !goBack ) {
            System.out.println( "\n1. Add New Section" );
            System.out.println( "2. Go Back" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:
                    addNewSectionAdmin( scanner, conn, eTextbookID, chapterID );
                    break;
                case 2:
                    System.out.println( "\nReturning to Create pervious page...\n" );
                    goBack = true;
                    break;
                case 3:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addNewSectionAdmin ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID ) {
        System.out.println( "\nAdd New Section:" );

        String sectionID = "";
        boolean validInput = false;

        // Loop until a valid section number is entered
        while ( !validInput ) {
            System.out.print( "Enter Section Number (numeric): " );
            if ( scanner.hasNextInt() ) {
                final int sectionNumInput = scanner.nextInt();
                sectionID = String.format( "sec%02d", sectionNumInput ); // Format
                                                                         // as
                                                                         // "sec"
                                                                         // +
                                                                         // two-digit
                                                                         // number
                validInput = true;
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric section number." );
                scanner.next(); // Clear invalid input
            }
        }

        // Clear the newline character left after reading the section number
        scanner.nextLine();

        // Prompt the user to enter the section title and capture the entire
        // line
        System.out.print( "Enter Section Title: " );

        final String sectionTitle = scanner.nextLine();

        try {
            // Insert section data into the database
            DBConnect.insertSectionData( conn, eTextbookID, chapterID, sectionID, sectionTitle, null );
            System.out.println( "Section added successfully." );
        }
        catch ( final SQLException e ) {
            e.printStackTrace();
        }

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add New Content Block" );
            System.out.println( "2. Go Back" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:

                    addNewContentBlockAdmin( scanner, conn, eTextbookID, chapterID, sectionID );
                    break;
                case 2:
                    System.out.println( "\nReturning to Add New Chapter page...\n" );
                    goBack = true;
                    break;
                case 3:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addNewContentBlockAdmin ( final Scanner scanner, final Connection conn,
            final String eTextbookID, final String chapterID, final String sectionID ) {
        System.out.println( "\nAdd New Content Block:" );

        String contentBlockID = "";
        boolean validInput = false;

        while ( !validInput ) {
            System.out.print( "Enter Content Block ID (numeric): " );
            if ( scanner.hasNextInt() ) {
                final int blockNumInput = scanner.nextInt();
                contentBlockID = String.format( "block%02d", blockNumInput ); // Format
                                                                              // as
                                                                              // "block"
                                                                              // +
                                                                              // two-digit
                                                                              // number
                validInput = true;
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric content block ID." );

                scanner.next(); // Clear invalid input
            }
        }

        // Clear the newline character left after reading the block number
        scanner.nextLine();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add Text" );
            System.out.println( "2. Add Picture" );
            System.out.println( "3. Add Activity" );
            System.out.println( "4. Go Back" );
            System.out.println( "5. Landing Page" );

            final int choice = getUserChoice( scanner, 5 );

            switch ( choice ) {
                case 1:

                    addTextAdmin( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID );
                    break;
                case 2:
                    addPictureAdmin( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID );
                    break;
                case 3:
                    addActivityAdmin( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID );
                    break;
                case 4:
                    System.out.println( "\nReturning to previous page...\n" );
                    goBack = true;
                    break;
                case 5:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addTextAdmin ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID ) {
        scanner.nextLine();
        System.out.println( "\nAdd Text:" );

        // Prompt for multi-word text content
        System.out.print( "Enter Text Content: " );
        final String textContent = scanner.nextLine(); // Read the entire line
                                                       // for multi-word input

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add" );
            System.out.println( "2. Go Back" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:

                    try {
                        DBConnect.insertBlockData( conn, eTextbookID, chapterID, sectionID, contentBlockID, "text",
                                textContent, null, permission );
                    }
                    catch ( final SQLException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println( "\nText added successfully:" );
                    System.out.println( "Content: " + textContent );
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back to Add New Content Block page...\n" );
                    goBack = true;
                    break;
                case 3:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addTextFac ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID ) {
        scanner.nextLine();
        System.out.println( "\nAdd Text:" );

        // Prompt for multi-word text content
        System.out.print( "Enter Text Content: " );
        final String textContent = scanner.nextLine(); // Read the entire line
                                                       // for multi-word input

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:

                    try {
                        DBConnect.insertBlockData( conn, eTextbookID, chapterID, sectionID, contentBlockID, "text",
                                textContent, null, permission );
                    }
                    catch ( final SQLException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println( "\nText added successfully:" );
                    System.out.println( "Content: " + textContent );
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back to previous page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addPictureAdmin ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID ) {
        System.out.println( "\nAdd Picture:" );
        System.out.print( "Enter Picture Information: " );
        final String pictureInfo = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add" );
            System.out.println( "2. Go Back" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:
                    try {
                        DBConnect.insertBlockData( conn, eTextbookID, chapterID, sectionID, contentBlockID, "picture",
                                pictureInfo, null, permission );
                    }
                    catch ( final SQLException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println( "\nPicture added successfully:" );
                    System.out.println( "Picture Information: " + pictureInfo );
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back to Add New Content Block page...\n" );
                    goBack = true;
                    break;
                case 3:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addPictureFac ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID ) {
        System.out.println( "\nAdd Picture:" );
        System.out.print( "Enter Picture Information: " );
        final String pictureInfo = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    try {
                        DBConnect.insertBlockData( conn, eTextbookID, chapterID, sectionID, contentBlockID, "picture",
                                pictureInfo, null, permission );
                    }
                    catch ( final SQLException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println( "\nPicture added successfully:" );
                    System.out.println( "Picture Information: " + pictureInfo );
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back to previous page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addActivityAdmin ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID ) {
        System.out.println( "\nAdd Activity:" );

        String activityID = "";
        boolean validInput = false;

        // Loop until a valid numeric activity ID is entered

        while ( !validInput ) {
            System.out.print( "Enter Unique Activity ID (numeric): " );
            if ( scanner.hasNextInt() ) {
                final int activityNumInput = scanner.nextInt();
                activityID = String.format( "ACT%02d", activityNumInput ); // Format
                                                                           // as
                                                                           // "ACT"
                                                                           // +
                                                                           // two-digit
                                                                           // number
                validInput = true;
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric activity ID." );

                scanner.next(); // Clear invalid input
            }
        }

        try {
            DBConnect.insertBlockData( conn, eTextbookID, chapterID, sectionID, contentBlockID, "activity", activityID,
                    null, permission );

            DBConnect.insertActivityData( conn, eTextbookID, chapterID, sectionID, contentBlockID, activityID, null, permission );
        }
        catch ( final SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Clear the newline character left after reading the activity number
        scanner.nextLine();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add Question" );
            System.out.println( "2. Go Back" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:
                    addQuestionAdmin( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID, activityID );
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back to previous page...\n" );
                    goBack = true;
                    break;
                case 3:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addActivityFac ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID ) {
        System.out.println( "\nAdd Activity:" );

        String activityID = "";
        boolean validInput = false;

        // Loop until a valid numeric activity ID is entered

        while ( !validInput ) {
            System.out.print( "Enter Unique Activity ID (numeric): " );
            if ( scanner.hasNextInt() ) {
                final int activityNumInput = scanner.nextInt();
                activityID = String.format( "ACT%02d", activityNumInput ); // Format
                                                                           // as
                                                                           // "ACT"
                                                                           // +
                                                                           // two-digit
                                                                           // number
                validInput = true;
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric activity ID." );

                scanner.next(); // Clear invalid input
            }
        }

        try {
            DBConnect.insertBlockData( conn, eTextbookID, chapterID, sectionID, contentBlockID, "activity", activityID,
                    null, permission );

            DBConnect.insertActivityData( conn, eTextbookID, chapterID, sectionID, contentBlockID, activityID, null , permission);
        }
        catch ( final SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Clear the newline character left after reading the activity number
        scanner.nextLine();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add Question" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    addQuestionFac( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID, activityID );
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back to previous page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addQuestionAdmin ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID, final String activityID ) {
        System.out.println( "\nAdd Question:" );

        String questionID = "";
        boolean validInput = false;

        // Loop until a valid numeric question ID is entered

        while ( !validInput ) {
            System.out.print( "Enter Question ID (numeric): " );
            if ( scanner.hasNextInt() ) {
                final int questionNumInput = scanner.nextInt();
                questionID = "Q" + questionNumInput; // Format as "Q" + the
                                                     // number
                validInput = true;
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric question ID." );

                scanner.next(); // Clear invalid input
            }
        }

        // Clear the newline character left after reading the question ID
        scanner.nextLine();

        // Input question text

        System.out.print( "Enter Question Text: " );

        final String questionText = scanner.nextLine();

        // Input options (Text, Explanation, Label)
        final String[] optionTexts = new String[4];
        final String[] optionExplanations = new String[4];
        final String[] optionLabels = new String[4];
        int answer = -1;

        for ( int i = 0; i < 4; i++ ) {
            System.out.print( "Enter Option " + ( i + 1 ) + " Text: " );
            optionTexts[i] = scanner.nextLine();

            System.out.print( "Enter Option " + ( i + 1 ) + " Explanation: " );
            optionExplanations[i] = scanner.nextLine();

            System.out.print( "Enter Option " + ( i + 1 ) + " Label (Correct or Incorrect): " );
            optionLabels[i] = scanner.next();
            scanner.nextLine(); // Consume newline

            // Check if the option is labeled as "Correct" and set answer to the
            // index
            if ( optionLabels[i].equalsIgnoreCase( "Correct" ) ) {

                answer = i + 1; // Store the correct answer index (1-based)
            }
        }

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Save" );
            System.out.println( "2. Cancel" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:

                    // Ensure answer is set to a valid option if marked as
                    // "Correct"
                    if ( answer == -1 ) {
                        System.out.println( "Error: No correct answer marked. Please try again." );
                    }
                    else {
                        try {
                            DBConnect.insertQuestionData( conn, eTextbookID, chapterID, sectionID, contentBlockID,
                                    activityID, questionID, questionText, optionTexts[0], optionExplanations[0],
                                    optionTexts[1], optionExplanations[1], optionTexts[2], optionExplanations[2],
                                    optionTexts[3], optionExplanations[3], answer );
                        }
                        catch ( final SQLException e ) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println( "\nQuestion saved successfully:" );
                        System.out.println( "Question ID: " + questionID );
                        System.out.println( "Question Text: " + questionText );

                        for ( int i = 0; i < 4; i++ ) {
                            System.out.println( "Option " + ( i + 1 ) + " Text: " + optionTexts[i] );
                            System.out.println( "Option " + ( i + 1 ) + " Explanation: " + optionExplanations[i] );
                            System.out.println( "Option " + ( i + 1 ) + " Label: " + optionLabels[i] );
                        }

                        // Redirect back to the add activity page
                        addActivityAdmin( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID );

                    }
                    break;

                case 2:
                    System.out.println( "\nDiscarding input and going back to Add Activity page...\n" );
                    goBack = true;
                    break;

                case 3:

                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

    private static void addQuestionFac ( final Scanner scanner, final Connection conn, final String eTextbookID,
            final String chapterID, final String sectionID, final String contentBlockID, final String activityID ) {
        System.out.println( "\nAdd Question:" );

        String questionID = "";
        boolean validInput = false;

        // Loop until a valid numeric question ID is entered

        while ( !validInput ) {
            System.out.print( "Enter Question ID (numeric): " );
            if ( scanner.hasNextInt() ) {
                final int questionNumInput = scanner.nextInt();
                questionID = "Q" + questionNumInput; // Format as "Q" + the
                                                     // number
                validInput = true;
            }
            else {
                System.out.println( "Invalid input. Please enter a numeric question ID." );

                scanner.next(); // Clear invalid input
            }
        }

        // Clear the newline character left after reading the question ID
        scanner.nextLine();

        // Input question text

        System.out.print( "Enter Question Text: " );

        final String questionText = scanner.nextLine();

        // Input options (Text, Explanation, Label)
        final String[] optionTexts = new String[4];
        final String[] optionExplanations = new String[4];
        final String[] optionLabels = new String[4];
        int answer = -1;

        for ( int i = 0; i < 4; i++ ) {
            System.out.print( "Enter Option " + ( i + 1 ) + " Text: " );
            optionTexts[i] = scanner.nextLine();

            System.out.print( "Enter Option " + ( i + 1 ) + " Explanation: " );
            optionExplanations[i] = scanner.nextLine();

            System.out.print( "Enter Option " + ( i + 1 ) + " Label (Correct or Incorrect): " );
            optionLabels[i] = scanner.next();
            scanner.nextLine(); // Consume newline

            // Check if the option is labeled as "Correct" and set answer to the
            // index
            if ( optionLabels[i].equalsIgnoreCase( "Correct" ) ) {

                answer = i + 1; // Store the correct answer index (1-based)
            }
        }

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Save" );
            System.out.println( "2. Cancel" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:

                    // Ensure answer is set to a valid option if marked as
                    // "Correct"
                    if ( answer == -1 ) {
                        System.out.println( "Error: No correct answer marked. Please try again." );
                    }
                    else {
                        try {
                            DBConnect.insertQuestionData( conn, eTextbookID, chapterID, sectionID, contentBlockID,
                                    activityID, questionID, questionText, optionTexts[0], optionExplanations[0],
                                    optionTexts[1], optionExplanations[1], optionTexts[2], optionExplanations[2],
                                    optionTexts[3], optionExplanations[3], answer );
                        }
                        catch ( final SQLException e ) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println( "\nQuestion saved successfully:" );
                        System.out.println( "Question ID: " + questionID );
                        System.out.println( "Question Text: " + questionText );

                        for ( int i = 0; i < 4; i++ ) {
                            System.out.println( "Option " + ( i + 1 ) + " Text: " + optionTexts[i] );
                            System.out.println( "Option " + ( i + 1 ) + " Explanation: " + optionExplanations[i] );
                            System.out.println( "Option " + ( i + 1 ) + " Label: " + optionLabels[i] );
                        }

                        // Redirect back to the add activity page
                        addActivityFac( scanner, conn, eTextbookID, chapterID, sectionID, contentBlockID );

                    }
                    break;

                case 2:
                    System.out.println( "\nDiscarding input and going back to Add Activity page...\n" );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }
    }

   private static void addStudentToCourse ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nAdd Student to Course:" );
        System.out.print( "Enter First Name: " );
        final String firstName = scanner.next();
        System.out.print( "Enter Last Name: " );
        final String lastName = scanner.next();
        System.out.print( "Email: " );
        final String email = scanner.next();
        System.out.print( "Course Token: " );
        final String courseToken = scanner.next();

        // Generate Student ID
        final String firstPart = firstName.substring( 0, 2 );
        final String lastPart = lastName.substring( 0, 2 );
        final String studentID = firstPart + lastPart + "1024";
        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Enroll" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:

                    try {
                        // Retrieve the course ID using the token
                        final String courseID = getCourseIdByToken( conn, courseToken );
                        if ( courseID == null ) {
                            System.out.println( "Invalid course token. Please try again." );
                            return;
                        }

                        // Check if there's enough room in the course
                        if ( isCourseFull( conn, courseID ) ) {
                            System.out.println( "The course is full. Cannot enroll the student." );
                        }
                        else {
                            // Enroll the student with 'Enrolled' status
                            DBConnect.insertEnrollmentData( conn, courseID, studentID, "Enrolled" );
                            System.out.println( "Student successfully enrolled in the course." );
                        }
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error adding student to course: " + e.getMessage() );
                    }
                case 2:
                    goBack = true;
                    break;
            }

        }

    }
  
  private static boolean isCourseFull ( final Connection conn, final String courseID ) throws SQLException {
        final String querySQL = "SELECT COUNT(*) AS enrolled_count, c.CourseCapacity " + "FROM Enrollment e "
                + "JOIN Course c ON e.course_id = c.course_id " + "WHERE e.course_id = ? AND e.Status = 'Enrolled'";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, courseID );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    final int enrolledCount = rs.getInt( "enrolled_count" );
                    final int courseCapacity = rs.getInt( "CourseCapacity" );
                    return enrolledCount >= courseCapacity;
                }
                else {
                    throw new SQLException( "Course not found." );
                }
            }
        }
    }
  
  private static String getCourseIdByToken ( final Connection conn, final String courseToken ) throws SQLException {
        final String querySQL = "SELECT course_id FROM Course WHERE Token = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( querySQL ) ) {
            pstmt.setString( 1, courseToken );
            try ( ResultSet rs = pstmt.executeQuery() ) {
                if ( rs.next() ) {
                    return rs.getString( "course_id" );
                }
                else {
                    return null; // Token not found
                }
            }
        }
    }

    private static void modifyChapterFac ( final Scanner scanner, final Connection conn, final String textbookID ) {
        System.out.println( "\nModify Chapter for Textbook ID: " + textbookID );

        // Prompt the user for the Unique Chapter ID
        System.out.print( "Enter Unique Chapter ID: " );
        final String chapterID = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Hide Chapter" );
            System.out.println( "2. Delete Chapter" );
            System.out.println( "3. Add New Section" );
            System.out.println( "4. Modify Section" );
            System.out.println( "5. Go Back" );

            final int choice = getUserChoice( scanner, 5 ); // Limit to 5
                                                            // options

            switch ( choice ) {
                case 1:
                    // Hide Chapter
                    try {
                        hideChapter( conn, textbookID, chapterID );
                        System.out.println( "Chapter successfully hidden." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error hiding chapter: " + e.getMessage() );
                    }
                    break;

                case 2:
                    // Delete Chapter
                    try {
                        deleteChapter( conn, textbookID, chapterID );
                        System.out.println( "Chapter successfully deleted." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error deleting chapter: " + e.getMessage() );
                    }
                    break;

                case 3:
                    // Add New Section
                    addNewSectionFac( scanner, conn, textbookID, chapterID );
                    break;

                case 4:
                    // Modify Section
                    modifySectionFac( scanner, conn, textbookID, chapterID );
                    break;

                case 5:
                    // Go Back to the previous menu
                    System.out.println( "\nReturning to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    private static void modifySectionFac ( final Scanner scanner, final Connection conn, final String textbookID,
            final String chapterID ) {
        System.out.println( "\nModify Section for Textbook ID: " + textbookID + ", Chapter ID: " + chapterID );

        // Prompt the user for the Unique Section ID
        System.out.print( "Enter Section Number: " );
        final String sectionID = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            // Display menu options
            System.out.println( "\n1. Hide Section" );
            System.out.println( "2. Delete Section" );
            System.out.println( "3. Add New Content Block" );
            System.out.println( "4. Modify Content Block" );
            System.out.println( "5. Go Back" );

            final int choice = getUserChoice( scanner, 5 ); // Limit to 5
                                                            // options

            switch ( choice ) {
                case 1:
                    // Hide Section
                    try {
                        hideSection( conn, textbookID, chapterID, sectionID );
                        System.out.println( "Section successfully hidden." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error hiding section: " + e.getMessage() );
                    }
                    break;

                case 2:
                    // Delete Section
                    try {
                        deleteSection( conn, textbookID, chapterID, sectionID );
                        System.out.println( "Section successfully deleted." );
                    }
                    catch ( final SQLException e ) {
                        System.out.println( "Error deleting section: " + e.getMessage() );
                    }
                    break;

                case 3:
                    // Add New Content Block
                    addNewContentBlockFac( scanner, conn, textbookID, chapterID, sectionID );
                    break;

                case 4:
                    // Modify Content Block
                    modifyContentBlockFac( scanner, conn, textbookID, chapterID, sectionID );
                    break;

                case 5:
                    // Go Back to the previous menu
                    System.out.println( "\nReturning to the previous menu." );
                    goBack = true;
                    break;

                default:
                    System.out.println( "Invalid choice. Please try again." );
            }
        }
    }

    private static void hideSection ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID ) throws SQLException {
        final String updateSQL = "UPDATE Section SET Hidden = 'yes' WHERE TextbookID = ? AND ChapterID = ? AND SectionID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Section successfully marked as hidden." );
            }
            else {
                System.out.println( "No section found with the provided ID." );
            }
        }
    }

    private static void deleteSection ( final Connection conn, final String textbookID, final String chapterID,
            final String sectionID ) throws SQLException {
        final String deleteSQL = "DELETE FROM Section WHERE TextbookID = ? AND ChapterID = ? AND SectionID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            pstmt.setString( 3, sectionID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Section successfully deleted." );
            }
            else {
                System.out.println( "No section found with the provided ID." );
            }
        }
    }

    private static void hideChapter ( final Connection conn, final String textbookID, final String chapterID )
            throws SQLException {
        final String updateSQL = "UPDATE Chapter SET Hidden = 'yes' WHERE TextbookID = ? AND ChapterID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( updateSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Chapter successfully marked as hidden." );
            }
            else {
                System.out.println( "No chapter found with the provided ID." );
            }
        }
    }

    private static void deleteChapter ( final Connection conn, final String textbookID, final String chapterID )
            throws SQLException {
        final String deleteSQL = "DELETE FROM Chapter WHERE TextbookID = ? AND ChapterID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement( deleteSQL ) ) {
            pstmt.setInt( 1, Integer.parseInt( textbookID ) );
            pstmt.setString( 2, chapterID );
            final int rowsAffected = pstmt.executeUpdate();
            if ( rowsAffected > 0 ) {
                System.out.println( "Chapter successfully deleted." );
            }
            else {
                System.out.println( "No chapter found with the provided ID." );
            }
        }
    }

    private static void addTeachingAssistantToCourse ( final Scanner scanner ) {
        System.out.println( "\nAdd Teaching Assistant to Course:" );
        System.out.print( "Enter First Name: " );
        final String firstName = scanner.next();
        System.out.print( "Enter Last Name: " );
        final String lastName = scanner.next();
        System.out.print( "Enter Email: " );
        final String email = scanner.next();
        System.out.print( "Enter Default Password: " );
        final String defaultPassword = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Add New TA" );
            System.out.println( "2. Go Back" );

            final int choice = getUserChoice( scanner, 2 );

            switch ( choice ) {
                case 1:
                    addTeachingAssistantToCourse( scanner );
                    goBack = true;
                    break;
                case 2:
                    System.out.println( "\nDiscarding input and going back...\n" );
                    goBack = true;
                    break;
                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }

    }

    private static void viewSection ( final Scanner scanner, final Connection conn ) {
        // TODO Auto-generated method stub

    }

    private static void viewPartActPoint ( final Scanner scanner, final Connection conn ) {
        // TODO Auto-generated method stub

    }

    private static void createNewActiveTextbook ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nCreate New Active Textbook:" );

        // Input course details
        System.out.print( "Enter Unique Course ID: " );
        final String courseID = scanner.next();
        System.out.print( "Enter Unique ID of the E-textbook: " );
        final String eTextbookID = scanner.next();
        System.out.print( "Enter E-Textbook Title: " );
        final String eTextbookTitle = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Save" );
            System.out.println( "2. Cancel" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:
                    // Save the course data (for simplicity, we just print it)
                    System.out.println( "\nActive E-Textbook saved successfully:" );
                    System.out.println( "Course ID: " + courseID );
                    System.out.println( "E-textbook ID: " + eTextbookID );
                    System.out.println( "E-textbook Title: " + eTextbookTitle );
                    break;

                case 2:
                    System.out.println( "\nDiscarding input and going back to the previous page...\n" );
                    goBack = true; // Return to the previous page without saving
                    break;

                case 3:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn ); 
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }

    }

    private static void createNewEvaluationTextbook ( final Scanner scanner, final Connection conn ) {
        System.out.println( "\nCreate New Evaluation Textbook:" );

        // Input course details
        System.out.print( "Enter Unique Course ID: " );
        final String courseID = scanner.next();
        System.out.print( "Enter Unique ID of the E-textbook: " );
        final String eTextbookID = scanner.next();
        System.out.print( "Enter E-Textbook Title: " );
        final String eTextbookTitle = scanner.next();

        boolean goBack = false;

        while ( !goBack ) {
            System.out.println( "\n1. Save" );
            System.out.println( "2. Cancel" );
            System.out.println( "3. Landing Page" );

            final int choice = getUserChoice( scanner, 3 );

            switch ( choice ) {
                case 1:
                    // Save the course data (for simplicity, we just print it)
                    System.out.println( "\nEvalutation E-Textbook saved successfully:" );
                    System.out.println( "Course ID: " + courseID );
                    System.out.println( "E-textbook ID: " + eTextbookID );
                    System.out.println( "E-textbook Title: " + eTextbookTitle );
                    break;

                case 2:
                    System.out.println( "\nDiscarding input and going back to the previous page...\n" );
                    goBack = true; // Return to the previous page without saving
                    break;

                case 3:
                    System.out.println( "\nDiscarding input and returning to Landing Page...\n" );
                    showAdminOperationsMenu( scanner, conn );
                    break;

                default:
                    System.out.println( "Invalid choice. Please enter a valid option." );
            }
        }

    }

    private static int getUserChoice ( final Scanner scanner, final int maxOption ) {
        int choice = -1;
        while ( choice < 1 || choice > maxOption ) {
            System.out.print( "Enter your choice (1-" + maxOption + "): " );
            if ( scanner.hasNextInt() ) {
                choice = scanner.nextInt();
            }
            else {
                System.out.println( "Invalid input. Please enter a number between 1 and " + maxOption + "." );
                scanner.next();
            }
        }
        return choice;
    }

    private static boolean login ( final String userType, final Scanner scanner ) {
        System.out.println( userType + " Login:" );
        System.out.print( "Enter username: " );
        final String username = scanner.next();
        System.out.print( "Enter password: " );
        final String password = scanner.next();

        if ( validateCredentials( username, password ) ) {
            userID = username;
            return true;
        }
        else {
            System.out.println( "Invalid credentials. Please try again.\n" );
            return false;
        }
    }

    // Method to validate credentials (dummy implementation)
    private static boolean validateCredentials ( final String username, final String password ) {
        // For demo purposes, assume any non-empty credentials are valid
        return !username.isEmpty() && !password.isEmpty();
    }
}

