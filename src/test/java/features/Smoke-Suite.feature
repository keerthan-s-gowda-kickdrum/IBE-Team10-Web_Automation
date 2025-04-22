Feature: Landing Page Verification


@LandingPage
  Scenario: Verify user is navigated to the landing page when the URL is entered in the browser
    Given  the user launches the browser
    And navigates to the landing page URL "https://dy7yrjoo64xt0.cloudfront.net/#/"
    When the landing page is loaded
    Then the banner image should be displayed


@LandingPage
  Scenario: Verify header elements are displayed
    Given the user is on the landing page
    Then the main header should be visible
    And the logo should be displayed
    And the booking engine name should be displayed
    And the bookings link should be present
    And the language dropdown should be available
    And the currency dropdown should be available
    And the login button should be visible


@LandingPage
  Scenario: Footer displays company logo and copyright information
    Given the user is on the landing page
    Then the footer should be visible
    And the company logo should be displayed in the footer
    And the footer should display the copyright information


@LandingPage
  Scenario: User selects 'Team 19 Hotel' from the Property name dropdown
    Given the user is on the landing page
    And the user selects a property from the "Team 19 Hotel" dropdown
    Then "Team 19 Hotel" should be displayed as the place holder of the Property name dropdown


@LandingPage
  Scenario: Search button should be disabled if only Property name is selected
    Given the user is on the landing page
    When the user selects "Team 19 Hotel" from the Property name dropdown
    Then the Search button should be disabled


@LandingPage
  Scenario: Validate Rate Calendar behavior based on selected dates
    Given the user is on the landing page
    When the user selects "Team 19 Hotel" from the Property name dropdown
    And the user clicks on the check-in/check-out field
    Then the rate calendar should be displayed
    When the user selects the check-in date from the config file
    Then the Apply button in the calendar should be disabled
    When the user selects a check-out date based on the configured range
    Then the Apply button in the calendar should be enabled
    When the user clicks the Apply button
    And the selected check-in and check-out dates should be displayed in the placeholder field

@LandingPage
  Scenario: User should be redirected to the room results page after selecting property and dates
    Given the user is on the landing page
    And the user selects "Team 19 Hotel" from the Property name dropdown
    And the user clicks on the check-in/check-out field
    And the user selects the check-in date from the config file
    And the user selects a check-out date based on the configured range
    When the user clicks the Apply button
    When the user clicks the Search button
    Then the user should be redirected to the room results page

# Smoke test suite for the room results page to ensure basic functionality is working.


@RoomResultsPage
  Scenario: Verify header elements are displayed
    Given the user launches the browser and navigates to the Room Results page
    Then the main header should be visible
    And the logo should be displayed
    And the booking engine name should be displayed
    And the bookings link should be present
    And the language dropdown should be available
    And the currency dropdown should be available
    And the login button should be visible


@RoomResultsPage
  Scenario: Footer displays company logo and copyright information
    Given the user launches the browser and navigates to the Room Results page
    Then the footer should be visible
    And the company logo should be displayed in the footer
    And the footer should display the copyright information

@RoomResultsPage
  Scenario: Verify the stepper is displayed and "Choose Room" step is highlighted on Room Results Page
    Given the user launches the browser and navigates to the Room Results page
    Then the stepper should be displayed
    And the "Choose Room" step should be highlighted as current

@RoomResultsPage
  Scenario: Room modal displays the selected amenities filter
    Given the user launches the browser and navigates to the Room Results page
    When the user increases the number of adults by one
    And the user clicks on the "Amenities" filter dropdown
    And the user selects the second filter option
    And the user clicks on the "Select Room" button of the displayed room
    Then the room modal should open
    And the modal should display the selected amenities filter

@RoomResultsPage
  Scenario: Sort rooms by price (Low to High)
    Given the user launches the browser and navigates to the Room Results page
    When the user increases the number of adults by one
    When the user clicks on the Sort By dropdown
    And the user selects Price Low to High from the dropdown
    Then the cost of the room cards displayed after the sort should be sorted in ascending order

@RoomResultsPage
    Scenario: Verify each room card displays all required information
    Given the user launches the browser and navigates to the Room Results page
    When the user increases the number of adults by one
    Then each of the room cards displayed should show the following details:
      | Room Title           |
      | Reviews              |
      | Ratings              |
      | Location             |
      | Details              |
      | Special Deal Label   |
      | Price                |
      | Select Room Button   |

  @RoomResultsPage
  Scenario: Apply valid promo code and verify success message and new deal
    Given the user launches the browser and navigates to the Room Modal page
    When the user enters a valid promo code in the promo code text field and clicks on Apply
    Then a success message should be displayed
    And a new deal card with the title should be displayed in the modal


  @RoomResultsPage
  Scenario: Display error message on entering invalid promo code
    Given the user launches the browser and navigates to the Room Modal page
    When the user enters an invalid promo code in the promo code text field and clicks on Apply
    Then an error message should be displayed
   
@RoomResultsPage
  Scenario: Close the room modal and return to room results page
    Given the user launches the browser and navigates to the Room Modal page
    When the user clicks on the close (X) button
    Then the stepper should be displayed
    And the "Choose Room" step should be highlighted as current
   
 


    


