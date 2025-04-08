Feature: Landing Page Verification

  Scenario: Verify user is navigated to the landing page when the URL is entered in the browser
    When the user enters the application URL in the browser
    Then the landing page should be displayed
