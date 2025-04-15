Feature: Landing Page Verification

  Scenario: Verify user is navigated to the landing page when the URL is entered in the browser
    Given  the user launches the browser
    And navigates to the landing page URL "https://dy7yrjoo64xt0.cloudfront.net/#/"
    When the landing page is loaded
    Then the banner image should be displayed

  Scenario: Verify header elements are displayed
    Given the user is on the landing page
    Then the main header should be visible
    And the logo should be displayed
    And the booking engine name should be displayed
    And the bookings link should be present
    And the language dropdown should be available
    And the currency dropdown should be available
    And the login button should be visible

  Scenario: Footer displays company logo and copyright information
    Given the user is on the landing page
    Then the footer should be visible
    And the company logo should be displayed in the footer
    And the footer should display the copyright information

  Scenario: User selects 'Team 19 Hotel' from the Property name dropdown
    Given the user is on the landing page
    And the user selects a property from the "Team 19 Hotel" dropdown
    Then "Team 19 Hotel" should be displayed as the place holder of the Property name dropdown

  Scenario: Search button should be disabled if only Property name is selected
    Given the user is on the landing page
    When the user selects "Team 19 Hotel" from the Property name dropdown
    Then the Search button should be disabled

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

Scenario: User should be redirected to the room results page after selecting property and dates
    Given the user is on the landing page
    And the user selects "Team 19 Hotel" from the Property name dropdown
    And the user clicks on the check-in/check-out field
    And the user selects the check-in date from the config file
    And the user selects a check-out date based on the configured range
    When the user clicks the Apply button
    When the user clicks the Search button
    Then the user should be redirected to the room results page


