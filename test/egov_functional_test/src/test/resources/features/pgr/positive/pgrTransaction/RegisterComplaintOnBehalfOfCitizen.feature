Feature: Registering a Complaint

  @PGR
  Scenario Outline: Creating a complaint on behalf of Citizen With Status as Completed & Rejected

    ### On Login Screen ###
    Given user on Login screen verifies signInText has visible value Sign In
    And user on Login screen types on username value 1212
    And user on Login screen types on password value 12345678
    And user on Login screen clicks on signIn

    ### On Homepage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the menu
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Officials Register Grievance
    And user on Home screen clicks on firstMenuItem

    ### On Create Complaint Grievance Screen Entering Contact Information ###
    And user on Grienvance screen will wait until the page loads
    And user on Grievance screen verifies contactInfo has visible value Contact Information
    And user on Grievance screen selects on receivingMode value Call
    And user on Grievance screen types on name value Akhila
    And user on Grievance screen types on mobileNumber value 2222222222
#    And user on Grievance screen types on email value email

    ### On Create Complaint Grievance Screen Entering Grievance Information ###
    And user on Grievance screen selects on grievanceCategory value Revenue
    And user on Grievance screen selects on grievanceType value Unauthorised Advt. Boards

    ### On Create Complaint Grievance Screen Entering More Details ###
    And user on Grievance screen types on grievanceDetails value Grievance Details
    And user on Grievance screen types on grievanceLocation suggestion box with value Bank Road
    And user on Grievance screen uploads on selectPhoto value pgrDocument.jpg
    And user on Grievance screen clicks on create

    ### On Create Complaint Grievance Screen verifying the details ###
    And user on Grievance screen copies the complaintNum to applicationNumber
    And user on Grievance screen clicks on view
    And user on Grievance screen will wait until the page loads
    And user on Grievance screen copies the userName to user

    ### Logout ###
    And Intent:LogoutIntentTest

    ### Login ###
    And DataIntent:LoginIntent
      | user |
      | 12345678 |

    ### On HomePage Screen ###
    And user on Home screen types on dashBoardSearch with above applicationNumber
    And user on Home screen opens on dashBoardSearch with above applicationNumber

    ### On Grievance Screen ###
    And user on Grievance screen will wait until the page loads
    And user on Grievance screen selects on changeStatus value <status>
    And user on Grievance screen types on comments value Comments
    And user on Grievance screen clicks on submitButton
    And user on Grievance screen clicks on okButton
    And user on Grievance screen will wait until the page loads
    And user on Grievance screen clicks on homeButton

    ### On HomePage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the menu
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Search Grievance
    And user on Home screen clicks on firstMenuItem

#    ### On SearchGrievance Screen ###
#    And user on SearchGrievance screen will wait until the page loads
#    And user on SearchGrievance screen types on crnComplaintNumber with above applicationNumber
#    And user on SearchGrievance screen clicks on crnSearchButton
#    And user on SearchGrievance screen verifies crnStatus has visible value <status>

    ### Logout ###
    And Intent:LogoutIntentTest

    Examples:
      | status    |
      | COMPLETED |
      | REJECTED  |

  @PGR
  Scenario Outline: Creating a complaint on behalf of Citizen With Status as Forwarded

    ### On Login Screen ###
    Given user on Login screen verifies signInText has visible value Sign In
    And user on Login screen types on username value ramana
    And user on Login screen types on password value demo
    And user on Login screen clicks on signIn

    ### On Homepage Screen ###
    And user on Home screen will wait until the page loads
    And user on Home screen will see the menu
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Officials Register Grievance
    And user on Home screen clicks on firstMenuItem

    ### On Create Complaint Grievance Screen Entering Contact Information ###
    And user on Grievance screen verifies contactInfo has visible value Contact Information
    And user on Grievance screen selects on receivingMode value Call
    And user on Grievance screen types on name value Akhila
    And user on Grievance screen types on mobileNumber value 2222222222
#    And user on Grievance screen types on email value --email

    ### On Create Complaint Grievance Screen Entering Grievance Information ###
    And user on Grievance screen selects on grievanceCategory value Revenue
    And user on Grievance screen selects on grievanceType value Unauthorised Advt. Boards

    ### On Create Complaint Grievance Screen Entering More Details ###
    And user on Grievance screen types on grievanceDetails value Grievance Details
    And user on Grievance screen types on grievanceLocation suggestion box with value Bank Road
    And user on Grievance screen uploads on selectPhoto value pgrDocument.jpg
    And user on Grievance screen clicks on create

    ### On Create Complaint Grievance Screen verifying the details ###
    And user on Grievance screen copies the complaintNum to applicationNumber
    And user on Grievance screen clicks on view
    And user on Home screen will wait until the page loads
    And user on Grievance screen copies the userName to user

    ### Logout ###
    And Intent:LogoutIntentTest

    ### Login ###
    And DataIntent:LoginIntent
      | user |
      | 12345678 |

    ### On HomePage Screen ###
    And user on Home screen types on dashBoardSearch with above applicationNumber
    And user on Home screen opens on dashBoardSearch with above applicationNumber

    ### On Grievance Screen ###
    And user on Home screen will wait until the page loads
    And user on Grievance screen selects on changeStatus value <status>

    And user on Grievance screen will see the forwardDepartment
    And user on Grievance screen selects on forwardDepartment value <department>
    And user on Grievance screen selects on forwardDesignation value <designation>
    And user on Grievance screen selects on forwardPosition value <position>

    And user on Grievance screen types on comments value Comments
    And user on Grievance screen clicks on submitButton
    And user on Grievance screen clicks on okButton
    And user on Home screen will wait until the page loads
    And user on Grievance screen clicks on homeButton

    ### Logout ###
    And Intent:LogoutIntentTest

    ### Login ###
    And DataIntent:LoginIntent
      | 2020 |
      | 12345678  |

    And user on Home screen types on dashBoardSearch with above applicationNumber
    And user on Home screen verifies applicationBox has visible value applicationNumber

    ### Logout ###
    And Intent:LogoutIntentTest

    Examples:
      | status    | department | designation      | position |
      | FORWARDED | ADMINISTRATION   | Commissioner | Jayashree   |


