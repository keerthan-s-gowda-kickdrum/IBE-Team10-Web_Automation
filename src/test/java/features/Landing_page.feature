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


