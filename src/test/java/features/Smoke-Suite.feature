Feature: Landing Page Verification


@SmokeSuite
  Scenario: Verify user is navigated to the landing page when the URL is entered in the browser
    Given  the user launches the browser
    And navigates to the landing page URL "https://d1thdrt9dod7xw.cloudfront.net/"
    When the landing page is loaded
    Then the banner image should be displayed


@SmokeSuite
  Scenario: Verify header elements are displayed
    Given the user is on the landing page
    Then the main header should be visible
    And the logo should be displayed
    And the booking engine name should be displayed
    And the bookings link should be present
    And the language dropdown should be available
    And the currency dropdown should be available
    And the login button should be visible


@SmokeSuite
  Scenario: Footer displays company logo and copyright information
    Given the user is on the landing page
    Then the footer should be visible
    And the company logo should be displayed in the footer
    And the footer should display the copyright information


@SmokeSuite
  Scenario: User selects 'Team 7 Hotel' from the Property name dropdown
    Given the user is on the landing page
    And the user selects a property from the "Team 7 Hotel" dropdown
    Then "Team 7 Hotel" should be displayed as the place holder of the Property name dropdown


@SmokeSuite
  Scenario: Search button should be disabled if only Property name is selected
    Given the user is on the landing page
    When the user selects "Team 7 Hotel" from the Property name dropdown
    Then the Search button should be disabled


@SmokeSuite
  Scenario: Validate Rate Calendar behavior based on selected dates
    Given the user is on the landing page
    When the user selects "Team 7 Hotel" from the Property name dropdown
    And the user clicks on the check-in/check-out field
    Then the rate calendar should be displayed
    When the user selects the check-in date from the config file
    Then the Apply button in the calendar should be disabled
    When the user selects a check-out date based on the configured range
    Then the Apply button in the calendar should be enabled
    When the user clicks the Apply button
    And the selected check-in and check-out dates should be displayed in the placeholder field

@SmokeSuite
  Scenario: User should be redirected to the room results page after selecting property and dates
    Given the user is on the landing page
    And the user selects "Team 7 Hotel" from the Property name dropdown
    And the user clicks on the check-in/check-out field
    And the user selects the check-in date from the config file
    And the user selects a check-out date based on the configured range
    When the user clicks the Apply button
    When the user clicks the Search button
    Then the user should be redirected to the room results page

# Smoke test suite for the room results page to ensure basic functionality is working.

@SmokeSuite
  Scenario: Verify header elements are displayed
    Given the user launches the browser and navigates to the Room Results page
    Then the main header should be visible
    And the logo should be displayed
    And the booking engine name should be displayed
    And the bookings link should be present
    And the language dropdown should be available
    And the currency dropdown should be available
    And the login button should be visible


@SmokeSuite
  Scenario: Footer displays company logo and copyright information
    Given the user launches the browser and navigates to the Room Results page
    Then the footer should be visible
    And the company logo should be displayed in the footer
    And the footer should display the copyright information

@SmokeSuite
  Scenario: Verify the stepper is displayed and "Choose Room" step is highlighted on Room Results Page
    Given the user launches the browser and navigates to the Room Results page
    Then the stepper should be displayed
    And the "Choose Room" step should be highlighted as current

@SmokeSuite
  Scenario: Room modal displays the selected amenities filter
    Given the user launches the browser and navigates to the Room Results page
    When the user increases the number of adults by one
    And the user clicks on the "Amenities" filter dropdown
    And the user selects the second filter option
    And the user clicks on the "Select Room" button of the displayed room
    Then the room modal should open
    And the modal should display the selected amenities filter

@SmokeSuite
  Scenario: Sort rooms by price (Low to High)
    Given the user launches the browser and navigates to the Room Results page
    When the user increases the number of adults by one
    When the user clicks on the Sort By dropdown
    And the user selects Price Low to High from the dropdown
    Then the cost of the room cards displayed after the sort should be sorted in ascending order

@SmokeSuite
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

  @SmokeSuite
  Scenario: Apply valid promo code and verify success message and new deal
    Given the user launches the browser and navigates to the Room Modal page
    When the user enters a valid promo code in the promo code text field and clicks on Apply
    Then a success message should be displayed
    And a new deal card with the title should be displayed in the modal


  @SmokeSuite
  Scenario: Display error message on entering invalid promo code
    Given the user launches the browser and navigates to the Room Modal page
    When the user enters an invalid promo code in the promo code text field and clicks on Apply
    Then an error message should be displayed
   
@SmokeSuite
  Scenario: Close the room modal and return to room results page
    Given the user launches the browser and navigates to the Room Modal page
    When the user clicks on the close (X) button
    Then the stepper should be displayed
    And the "Choose Room" step should be highlighted as current

@SmokeSuite
  Scenario: User selects the deal with the least price and navigates to the checkout page
    Given the user launches the browser and navigates to the Room Modal page
    When the user clicks on the Select Package button of the deal with the least price
    Then the user should be redirected to the checkout page


# Smoke test suite for the checkout page to ensure basic functionality is working.

@SmokeSuite
  Scenario: User navigates to the Checkout page and the step label is highlighted as current 
    Given the user launches the browser and navigates to the Checkout page
    Then the stepper should be displayed
    And the Checkout step label should be highlighted as current
    And the previous step label should be highlighted as completed 

@SmokeSuite
  Scenario: Verify header elements are displayed
    Given the user launches the browser and navigates to the Checkout page
    Then the main header should be visible
    And the logo should be displayed
    And the booking engine name should be displayed
    And the language dropdown should be available
    And the currency dropdown should be available
    And the login button should be visible


@SmokeSuite
  Scenario: Footer displays company logo and copyright information
    Given the user launches the browser and navigates to the Checkout page
    Then the footer should be visible
    And the company logo should be displayed in the footer
    And the footer should display the copyright information

@SmokeSuite
  Scenario Outline: User completes checkout with valid traveler, billing, OTP, and payment details and moves to Booking Confirmation page
    Given the user launches the browser and navigates to the Checkout page

    When the user enters "<travelerFirstName>", "<travelerLastName>", "<travelerPhoneNumber>", and "<travelerEmail>" in the payment info section and clicks on the NEXT: BILLING INFO button
    Then the billing info section should expand

    When the user enters "<billingFirstName>", "<billingLastName>", "<mailingAddress1>", "<mailingAddress2>", "<country>", "<state>", "<zipcode>", "<city>", "<billingPhoneNumber>", and "<billingEmail>" in the billing info section
    And clicks on the Next: Payment Info button
    Then the OTP Verification section should expand

    When the user enters OTP in the OTP field and clicks on the VERIFY OTP button
    Then the payment info section should expand

    When the user enters valid payment details like card number "<cardNumber>", expiry month "<expiryMonth>", expiry year "<expiryYear>", and CVV "<cvv>"
    And selects the Terms and Conditions checkbox
    And clicks on the Purchase button
    Then the user should be redirected to the Booking Confirmation page

    Examples:
  | travelerFirstName | travelerLastName | travelerPhoneNumber | travelerEmail           | billingFirstName | billingLastName | mailingAddress1     | mailingAddress2 | country | state       | zipcode | city       | billingPhoneNumber | billingEmail                    | cardNumber        | expiryMonth | expiryYear | cvv  |
  | John              | Doe              | 1234567890          | john.doe@email.com      | John             | Doe             | 123 Main Street     | Apt 101         | India   | Karnataka   | 560058  | Dallas     | 1234567890         | keerthan.gowda@kickdrumtech.com | 4111111111111111  | 12          | 2026       | 123  |


   @SmokeSuite
  Scenario: User clicks Continue Shopping button and verifies redirection to Room Results page
    Given the user launches the browser and navigates to the Checkout page
    When the user clicks on the Continue Shopping button
    Then the user should be redirected to the Room Results page
    And the "Choose Room" step should be highlighted as current
    And the Trip Itinerary panel should be displayed on the Room Results page


   @SmokeSuite
  Scenario: User removes an item from the checkout page and is redirected to the Landing page
    Given the user launches the browser and navigates to the Checkout page
    When the user clicks on the Remove link
    Then the user should be redirected to the Landing page


    


