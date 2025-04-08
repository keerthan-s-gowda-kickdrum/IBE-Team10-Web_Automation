Feature: Room Results Page Verification


  #================== Banner Tests ==================

  Scenario: Verify that the banner displays with the same image as on the landing page
    Given the user is on the Room Results page
    Then the banner image should be the same as displayed on the landing page

  #================== Stepper Tests ==================

  Scenario: Verify that the stepper is visible and highlights the current step correctly when navigating forward and backward
    Given the user is on the Choose Room page
    Then the stepper should be visible
    And the "Choose Room" step should be highlighted as current

    When the user selects a room
    Then the user should be navigated to the "Choose Add-on" step
    And the "Choose Add-on" step should be highlighted as current

    When the user clicks on the "Choose Room" step in the stepper
    Then the user should be navigated back to the "Choose Room" page
    And the "Choose Room" step should be highlighted as current

  Scenario: Verify that a checkmark appears in the stepper only after a step is reached
    Given the user is on the Choose Room page
    Then the "Choose Room" step should be highlighted as current
    And the "Choose Room" step should be marked with a checkmark
    And future steps should not be marked with a checkmark

    When the user selects a room
    Then the user should be navigated to the "Choose Add-on" step
    And the "Choose Room" step should still have a checkmark
    And the "Choose Add-on" step should be highlighted as current
    And the "Choose Add-on" step should not have a checkmark yet

    When the user proceeds to the "Checkout" step
    Then the "Choose Add-on" step should now have a checkmark
    And the "Checkout" step should be highlighted as current
    And the "Checkout" step should not have a checkmark yet

  Scenario: Verify that the user cannot proceed to a future step without completing the current step
    Given the user is on the Choose Room page

    When the user tries to navigate to the "Choose Add-on" step without selecting a room
    Then the user should remain on the "Choose Room" step
    And an error message should be displayed indicating the step cannot be skipped

    When the user selects a room
    Then the user should be navigated to the "Choose Add-on" step

    When the user tries to navigate to the "Checkout" step without selecting an add-on (if required)
    Then the user should remain on the "Choose Add-on" step
    And an error message should be displayed indicating the step cannot be skipped

  Scenario: Verify that users can navigate back to previous steps
    Given the user is on the Choose Room page

    When the user selects a room
    And the user is navigated to the "Choose Add-on" step
    Then the stepper should highlight the "Choose Add-on" step as current

    When the user clicks on the "Choose Room" step in the stepper
    Then the user should be navigated back to the "Choose Room" page
    And the previously selected room should remain selected
    And the "Select Room" button should change to "Edit Selection"

    When the user proceeds again to the "Choose Add-on" step
    And the user selects an add-on
    And the user navigates to the "Checkout" step
    Then the stepper should highlight the "Checkout" step as current

    When the user clicks on the "Choose Add-on" step in the stepper
    Then the user should be navigated back to the "Choose Add-on" step
    And the previously selected add-on should still be selected

    When the user clicks on the "Choose Room" step in the stepper
    Then the user should be navigated back to the "Choose Room" page
    And the previously selected room should still be displayed
    And the "Select Room" button should remain as "Edit Selection"

  Scenario: Verify that clicking on a previous step does not reset entered data in the current step.
    Given the user is on the Choose Room page

    When the user selects a room
    And the user is navigated to the "Choose Add-on" step
    Then the stepper should highlight the "Choose Add-on" step as current

    When the user selects an add-on
    And the user navigates to the "Checkout" step
    Then the stepper should highlight the "Checkout" step as current

    When the user enters traveler details
    And the user enters payment details

    When the user clicks on the "Choose Add-on" step in the stepper
    Then the user should be navigated back to the "Choose Add-on" step
    And the previously selected add-on should still be selected

    When the user clicks on the "Checkout" step in the stepper
    Then the user should be navigated back to the "Checkout" step
    And the previously entered traveler details should still be present
    And the previously entered payment details should still be present

  #================== Search Form Tests ==================

  Scenario Outline: Verify that the search form contains the same fields and values as the landing page
    Given the user is on the Landing Page
    When the user enters search criteria
      | Field          | Value                |
      | Property Name  | <propertyName>        |
      | Check-in Date  | <checkInDate>        |
      | Check-out Date | <checkOutDate>       |
      | Guests        | <guests>             |
      | Rooms         | <rooms>              |

    And the user submits the search
    Then the user should be navigated to the Room Results page
    And the search form on the Room Results page should contain the same fields and values as entered on the Landing Page

    Examples:
      | propertyName | checkInDate | checkOutDate | guests           | rooms |
      | Property A   | 2025-04-10  | 2025-04-15   | 2 Adults, 1 Child | 1     |
      | Property A   | 2025-06-01  | 2025-06-05   | 3 Adults          | 2     |
      | Property A   | 2025-08-15  | 2025-08-20   | 2 Adults          | 1     |
      | Property A   | 2025-10-20  | 2025-10-25   | 4 Adults          | 2     |
      | Property A   | 2025-12-05  | 2025-12-10   | 2 Adults, 2 Children | 1 |

  Scenario: Verify that previously hidden fields on the Landing Page are now displayed with correct default values
    Given the user is on the Landing Page
    And the user has not expanded the additional options for "Guests", "Rooms", or "Accessibility"

    When the user submits the search
    Then the user should be navigated to the Room Results page
    And the previously hidden fields should now be displayed with correct default values:
      | Field               | Default Value |
      | Guests              | 2 Adults     |
      | Rooms               | 1 Room       |
      | Accessibility Checkbox | false  |

  Scenario Outline: Verify that users can update search criteria on the Room Results page
    Given the user is on the Room Results page
    And the search form is visible with pre-filled values from the Landing Page

    When the user updates the search parameter "<parameter>" to "<newValue>"
    And clicks on the "Search" button
    Then the search results should update based on the new criteria
    And the search results should reflect the updated "<parameter>" with "<newValue>"

    Examples:
      | parameter      | newValue       |
      | guests         | 3 Adults       |
      | rooms          | 2              |
      | beds           | 2              |
      | checkIn        | 2025-05-01     |
      | checkOut       | 2025-05-05     |

  Scenario Outline: Validate that the updated criteria persist after page refresh
    Given the user is on the Room Results page
    And the search form is visible with pre-filled values from the Landing Page

    When the user updates the search parameter "<parameter>" to "<newValue>"
    And clicks on the "Search" button
    Then the search results should update based on the new criteria

    When the user refreshes the page
    Then the search form should retain the updated "<parameter>" criteria as "<newValue>"
    And the search results should still reflect the updated criteria

    Examples:
      | parameter      | newValue       |
      | guests         | 3 Adults       |
      | rooms          | 2              |
      | beds           | 2              |
      | checkIn        | 2025-05-01     |
      | checkOut       | 2025-05-05     |

  Scenario Outline: Verify that modifying search parameters and clicking Search updates the room results
    Given the user is on the Room Results page

    When the user modifies the search parameters "<parameter>" to "<value>"
    And the user clicks on the "Search" button
    Then the room results should update based on the new search criteria "<parameter>" and "<value>"

    Examples:
      | parameter        | value      |
      | Check-in date    | 2025-04-10 |
      | Check-out date   | 2025-04-15 |
      | Number of rooms  | 2          |
      | Number of guests | 4          |
      | Number of beds   | 2          |

  Scenario Outline: Modifying search parameters and clicking Search updates the query parameters in the URL
    Given the user is on the Room Results page
    And the search form is visible with pre-filled values from the Landing Page

    When the user updates the search parameter "<parameter>" to "<newValue>"
    And the user clicks on the "Search" button
    Then the URL should contain the query parameter "<parameter>=<encodedValue>"

    Examples:
      | parameter | newValue    | encodedValue       |
      | guests    | 3 Adults    | guests=3%20Adults  |
      | checkIn   | 2025-05-01  | checkIn=2025-05-01 |
      | checkOut  | 2025-05-05  | checkOut=2025-05-05 |
      | rooms     | 2           | rooms=2            |
      | beds      | 3           | beds=3             |

  Scenario Outline: Verify that an appropriate error message is displayed when invalid or missing input is provided in the search form via URL
    Given the user is on the Room Results page
    And the search form is visible

    When the user manually modifies the URL to include "<parameter>=<value>"
    And the user refreshes the page
    Then an error message "<errorMessage>" should be displayed

    Examples:
      | parameter      | value          | errorMessage                                |
      | checkIn        | (removed)      | Please select a check-in date               |
      | checkOut       | (removed)      | Please select a check-out date              |
      | checkIn        | 2022-01-01     | Check-in date cannot be in the past         |
      | checkOut       | 2025-01-01     | Check-out date must be after check-in date |
      | checkIn        | invalidDate    | error message related to invalid checkIn date |
      | checkOut       | invalidDate    | error message related to invalid checkOut date |
      | guests         | -3             | Number of guests must be a positive number |
      | guests         | abc            | error message related to invalid guests    |
      | rooms          | 0              | Number of rooms must be greater than zero  |
      | rooms          | -2             | Number of rooms must be greater than zero  |
      | rooms          | abc            | error message related to invalid rooms     |
      | beds           | -1             | Number of beds must be a positive number   |
      | beds           | abc            | error message related to invalid beds      |
      | propertyId     | abc            | error message related to invalid propertyId |
      | propertyId     | -1             | error message related to invalid propertyId |
      | accessibility  | invalidValue   | error message related to invalid accessibility value |
      | tenantId       | abc            | error message related to invalid tenantId |
      | tenantId       | -1             | error message related to invalid tenantId |

  Scenario Outline: Search form handles missing parameters via URL
    Given the user is on the Room Results page
    And the search form is visible

    When the user manually modifies the URL to remove the "<parameter>" parameter
    And the user refreshes the page
    Then the search form should not reflect the missing "<parameter>" parameter
    And an error message related to missing "<parameter>" should be displayed

    Examples:
      | parameter      |
      | guests         |
      | rooms          |
      | beds           |
      | checkIn        |
      | checkOut       |
      | propertyId     |
      | accessibility  |
      | tenantId       |


    #================== Room Cards Tests ==================

  Scenario: Verify that each room card displays an image carousel
    Given the user is on the Room Results page
    When the room results are displayed
    Then each room card should display an image carousel

  Scenario: Verify that the image carousel auto-scrolls
    Given the user is on the Room Results page
    When the room results are displayed
    Then each room card with an image carousel should automatically scroll through the images

  Scenario: Ensure the image carousel loops correctly after reaching the last image
    Given the user is on the Room Results page
    When the room results are displayed
    Then each room card with an image carousel should loop back to the first image after reaching the last image

  Scenario: Verify that users can manually navigate the carousel using left and right arrow buttons
    Given the user is on the Room Results page
    When the room results are displayed
    Then each room card with an image carousel should display left and right arrow buttons
    And clicking the left arrow button should display the previous image
    And clicking the right arrow button should display the next image

  Scenario: Verify that room cards display all required information
    Given the user is on the Room Results page
    When the room results are displayed
    Then each room card should display:
      | Element             | Expected Presence                                                                                                                                                                                                                                                               |
      | Image Carousel      | An image carousel                                                                                                                                                                                                                                                               |
      | Room Title          | The title of the room                                                                                                                                                                                                                                                            |
      | Ratings & Reviews   | Ratings and reviews or a label indicating new room                                                                                                                                                                                                                             |
      | Location of Room    | The location of the room                                                                                                                                                                                                                                                         |
      | Room Details        | Room size, number of beds, and maximum occupancy                                                                                                                                                                                                                               |
      | Deals (if available)| The best available deal, if a deal exists                                                                                                                                                                                                                                        |
      | Room Prices         | The calculated price                                                                                                                                                                                                                                                            |
      | "Select Room" Button| A button labeled "Select Room"                                                                                                                                                                                                                                                 |

  Scenario: Verify that the "New Room" label appears when no ratings exist
    Given the user is on the Room Results page
    When the room results are displayed
    Then each room card without ratings should display the "New Room" label

  Scenario Outline: Verify that room prices are calculated correctly
    Given the user is on the Room Results page
    When the room results are displayed
    Then the displayed room price should be "<expectedPrice>"
    And the calculation should be: "<averagePriceForStay>" (average price for the stay) x "<rooms>" (rooms) = "<expectedPrice>"

    Examples:
      | averagePriceForStay | rooms | expectedPrice |
      | 132                 | 4     | 528           |
      | 150                 | 2     | 300           |
      | 200                 | 3     | 600           |
      | 100                 | 1     | 100           |

    #================== Filters Tests ==================
  @NotToBeAutomated
  Scenario: Verify that filters are displayed correctly as per tenant configuration
    Given the user is on the Room Results page
    And the tenant configuration specifies the following filters:
      | Filter Name | Displayed |
      | Amenities   | true      |
      | Beds        | true      |
    When the room results are displayed
    Then the filters should be displayed according to the tenant configuration:
      | Filter Name | Displayed |
      | Amenities   | true      |
      | Beds        | true      |

  @NotToBeAutomated
  Scenario: Verify that filters are not displayed when configured as invisible
    Given the user is on the Room Results page
    And the tenant configuration specifies the following filters:
      | Filter Name | Displayed |
      | Amenities   | false     |
      | Beds        | false     |
    When the room results are displayed
    Then the filters "Amenities" and "Beds" should not be displayed

  Scenario: Verify that selecting filter options updates the room results
    Given the user is on the Room Results page
    And the room results are initially displayed
    When the user selects the filter option "Amenities: Minibar"
    Then the room results should update to display only rooms with the "Minibar" amenity

  Scenario: Verify that selecting multiple filter options updates the room results correctly
    Given the user is on the Room Results page
    And the room results are initially displayed
    When the user selects the filter option "Amenities: Minibar"
    And the user selects the filter option "Beds: Double Bed"
    Then the room results should update to display only rooms with the "Minibar" amenity and "Double Bed"

  Scenario: Verify that deselecting filter options updates the room results correctly
    Given the user is on the Room Results page
    And the room results are filtered by "Amenities: Minibar"
    When the user deselects the filter option "Amenities: Minibar"
    Then the room results should update to display all rooms

  Scenario: Verify that clearing all filters updates the room results correctly
    Given the user is on the Room Results page
    And the room results are filtered by "Amenities: Minibar" and "Beds: Double Bed"
    When the user clears all filters
    Then the room results should update to display all rooms

     #================== Sorting Tests ==================
  @NotToBeAutomated
  Scenario: Verify that sorting options are displayed as per tenant configuration
    Given the user is on the Room Results page
    And the tenant configuration specifies the following sorting options:
      | Sorting Option | Displayed |
      | Price          | true      |
      | Rating         | true      |
      | Review         | true      |
    When the room results are displayed
    Then the sorting options should be displayed according to the tenant configuration:
      | Sorting Option | Displayed |
      | Price          | true      |
      | Rating         | true      |
      | Review         | true      |

  @NotToBeAutomated
  Scenario: Verify that sorting options are not displayed when configured as invisible
    Given the user is on the Room Results page
    And the tenant configuration specifies the following sorting options:
      | Sorting Option | Displayed |
      | Price          | false     |
      | Rating         | false     |
      | Review         | false     |
    When the room results are displayed
    Then the sorting options "Price", "Rating", and "Review" should not be displayed

  Scenario: Verify that selecting a sorting option arranges the room results accordingly for all tenant configured options
    Given the user is on the Room Results page
    And the tenant configuration specifies the following sorting options:
      | Sorting Option | Displayed |
      | Price          | true      |
      | Rating         | true      |
      | Review         | true      |
    When the user selects the sorting option "Price: High to Low"
    Then the room results should be arranged by price from highest to lowest

    When the user selects the sorting option "Price: Low to High"
    Then the room results should be arranged by price from lowest to highest

    When the user selects the sorting option "Rating: High to Low"
    Then the room results should be arranged by rating from highest to lowest

    When the user selects the sorting option "Rating: Low to High"
    Then the room results should be arranged by rating from lowest to highest

    When the user selects the sorting option "Review: High to Low"
    Then the room results should be arranged by review count from highest to lowest

    When the user selects the sorting option "Review: Low to High"
    Then the room results should be arranged by review count from lowest to highest

  Scenario: Verify that filtering and sorting together return correct room results
    Given the user is on the Room Results page
    And the room results are initially displayed
    When the user selects the filter option "Amenities: Minibar"
    And the user selects the sorting option "Price: Low to High"
    Then the room results should be filtered by "Amenities: Minibar"
    And the room results should be sorted by price from lowest to highest

  Scenario: Verify that multiple filters and sorting return correct room results
    Given the user is on the Room Results page
    And the room results are initially displayed
    When the user selects the filter option "Amenities: Minibar"
    And the user selects the filter option "Beds: Double Bed"
    And the user selects the sorting option "Rating: High to Low"
    Then the room results should be filtered by "Amenities: Minibar" and "Beds: Double Bed"
    And the room results should be sorted by rating from highest to lowest

  Scenario: Verify that changing sorting after filtering updates room results correctly
    Given the user is on the Room Results page
    And the room results are filtered by "Amenities: Minibar"
    And the room results are sorted by "Price: Low to High"
    When the user selects the sorting option "Price: High to Low"
    Then the room results should be filtered by "Amenities: Minibar"
    And the room results should be sorted by price from highest to lowest

  Scenario: Verify that changing filters after sorting updates room results correctly
    Given the user is on the Room Results page
    And the room results are sorted by "Rating: High to Low"
    When the user selects the filter option "Beds: Double Bed"
    Then the room results should be filtered by "Beds: Double Bed"
    And the room results should be sorted by rating from highest to lowest

  Scenario: Verify filter and sorting persistence across searches
    Given the user is on the Room Results page
    And the room results are initially displayed
    When the user selects the filter option "Amenities: Minibar"
    And the user selects the sorting option "Price: Low to High"
    And the user updates the search criteria and clicks "Search"
    Then the room results should still be filtered by "Amenities: Minibar"
    And the room results should still be sorted by "Price: Low to High"

  Scenario: Verify filter and sorting persistence across browser tabs
    Given the user is on the Room Results page
    And the room results are initially displayed
    When the user selects the filter option "Amenities: Minibar"
    And the user selects the sorting option "Price: Low to High"
    And the user opens a new browser tab with the same Room Results page URL
    Then the room results in the new tab should be filtered by "Amenities: Minibar"
    And the room results in the new tab should be sorted by "Price: Low to High"

  Scenario: Verify filter and sorting persistence after page refresh
    Given the user is on the Room Results page
    And the room results are initially displayed
    When the user selects the filter option "Amenities: Minibar"
    And the user selects the sorting option "Price: Low to High"
    And the user refreshes the page
    Then the room results should still be filtered by "Amenities: Minibar"
    And the room results should still be sorted by "Price: Low to High"

      #================== Pagination Tests ==================

  Scenario: Verify that pagination indicates the number of results displayed and the total number of results available
    Given the user is on the Room Results page
    And the room results are displayed
    Then the pagination should display the number of results currently displayed (e.g., "1-10 of 50")
    And the pagination should display the total number of results available (e.g., "50")

  Scenario: Verify that pagination updates correctly when filters are applied
    Given the user is on the Room Results page
    And the room results are displayed with 50 results
    When the user applies the filter "Amenities: Minibar" which reduces the results to 25
    Then the pagination should update to display the filtered result count (e.g., "1-10 of 25")
    And the pagination should display the updated total number of filtered results (e.g., "25")

  Scenario: Verify that pagination updates correctly when sorting is applied
    Given the user is on the Room Results page
    And the room results are displayed with 50 results
    When the user applies the sorting "Price: Low to High"
    Then the pagination should still display the original result count (e.g., "1-10 of 50")
    And the pagination should still display the original total number of results (e.g., "50")

  Scenario: Verify that pagination correctly displays the last page of results
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user navigates to the last page
    Then the pagination should display the last set of results (e.g., "41-50 of 50")

  Scenario: Verify that pagination correctly displays the first page of results
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user navigates to the first page
    Then the pagination should display the first set of results (e.g., "1-10 of 50")

  Scenario: Verify that pagination is not displayed when there are no results
    Given the user is on the Room Results page
    And the room results are displayed with no results
    Then the pagination should not be displayed

  Scenario: Verify that pagination navigation buttons are disabled on first and last pages
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user is on the first page of results (e.g., "1-10 of 50")
    Then the "Previous" page buttons should be disabled
    When the user navigates to the last page of results
    Then the "Next" page buttons should be disabled

  Scenario: Verify that pagination navigation works correctly
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user clicks the "Next" page button
    Then the next page of results should be displayed (e.g., "11-20 of 50")

  Scenario: Verify that pagination navigation works correctly to the last page
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user clicks the "Last" page button
    Then the last page of results should be displayed (e.g., "41-50 of 50")

  Scenario: Verify that pagination navigation works correctly to the previous page
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    And the user is on the second page of results (e.g., "11-20 of 50")
    When the user clicks the "Previous" page button
    Then the first page of results should be displayed (e.g., "1-10 of 50")

  Scenario: Verify that pagination navigation works correctly to the first page
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    And the user is on the last page of results (e.g., "41-50 of 50")
    When the user clicks the "First" page button
    Then the first page of results should be displayed (e.g., "1-10 of 50")


  Scenario: Verify that pagination navigation works correctly when results are filtered
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user applies the filter "Amenities: Minibar" which reduces the results to 25
    And the user clicks the "Next" page button
    Then the next page of filtered results should be displayed (e.g., "11-20 of 25")

  Scenario: Verify that pagination updates correctly when filters and sorting options change
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user applies the filter "Amenities: Minibar" which reduces the results to 25
    And the user selects the sorting option "Price: Low to High"
    Then the pagination should update to display the filtered result count (e.g., "1-10 of 25")
    And the pagination should display the updated total number of filtered results (e.g., "25")
    And the room results should be sorted by price from lowest to highest

  Scenario: Verify that pagination updates correctly when filters are removed and sorting is changed
    Given the user is on the Room Results page
    And the room results are displayed with 25 results and 10 results per page, filtered by "Amenities: Minibar"
    And the room results are sorted by "Price: Low to High"
    When the user clears all filters
    And the user selects the sorting option "Rating: High to Low"
    Then the pagination should update to display the original result count (e.g., "1-10 of 50")
    And the pagination should display the original total number of results (e.g., "50")
    And the room results should be sorted by rating from highest to lowest

  Scenario: Verify that pagination updates correctly when sorting is changed and filters are applied
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    And the room results are sorted by "Price: Low to High"
    When the user applies the filter "Beds: Double Bed" which reduces the results to 30
    And the user selects the sorting option "Rating: High to Low"
    Then the pagination should update to display the filtered result count (e.g., "1-10 of 30")
    And the pagination should display the updated total number of filtered results (e.g., "30")
    And the room results should be sorted by rating from highest to lowest

  Scenario: Verify pagination updates correctly when multiple filters and sorting are applied
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user applies the filter "Amenities: Minibar"
    And the user applies the filter "Beds: Double Bed"
    And the user selects the sorting option "Review: High to Low"
    Then the pagination should update to display the filtered result count based on both filters
    And the pagination should display the updated total number of filtered results
    And the room results should be sorted by review count from highest to lowest

  Scenario: Verify that refreshing the page does not reset the pagination incorrectly
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user navigates to the third page of results (e.g., "21-30 of 50")
    And the user refreshes the page
    Then the pagination should still display the third page of results (e.g., "21-30 of 50")

  Scenario: Verify that refreshing the page after filtering does not reset the pagination incorrectly
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user applies the filter "Amenities: Minibar" which reduces the results to 25
    And the user navigates to the second page of filtered results (e.g., "11-20 of 25")
    And the user refreshes the page
    Then the pagination should still display the second page of filtered results (e.g., "11-20 of 25")

  Scenario: Verify that refreshing the page after sorting does not reset the pagination incorrectly
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user selects the sorting option "Price: Low to High"
    And the user navigates to the fourth page of results
    And the user refreshes the page
    Then the pagination should still display the fourth page of sorted results

  Scenario: Verify that refreshing the page after filtering and sorting does not reset the pagination incorrectly
    Given the user is on the Room Results page
    And the room results are displayed with 50 results and 10 results per page
    When the user applies the filter "Amenities: Minibar" which reduces the results to 25
    And the user selects the sorting option "Rating: High to Low"
    And the user navigates to the third page of filtered and sorted results
    And the user refreshes the page
    Then the pagination should still display the third page of filtered and sorted results

    #================== Special Deals Tests ==================
  Scenario: Verify that special deals are displayed correctly when available
    Given the user is on the Room Results page
    And a room with special deals is displayed
    When the room results are displayed
    Then the special deals should be displayed for the room


  Scenario: Verify that special deals are not displayed when unavailable
    Given the user is on the Room Results page
    And a room without special deals is displayed
    When the room results are displayed
    Then the special deals should not be displayed for the room

    #================== Edge Cases Tests ==================

  Scenario Outline: Verify that manually modifying invalid URL parameters does not reflect in the search form
    Given the user is on the Room Results page
    And the search form has default values
    When the user manually modifies the URL to include "<parameter>=<value>"
    And the user refreshes the page
    Then the search form should still display the default values
    And the search form should not reflect the invalid parameter "<parameter>" with value "<value>"

    Examples:
      | parameter      | value          |
      | checkIn        | invalidDate    |
      | checkOut       | 2022-01-01     |
      | guests         | -3             |
      | rooms          | abc            |
      | beds           | invalidValue   |
      | propertyId     | abc            |
      | accessibility  | nonBoolean     |
      | tenantId       | -1             |
      | unknownParam   | someValue      |

  Scenario: Verify that applying extreme filters returning zero results displays a proper "No Results Found" message
    Given the user is on the Room Results page
    And the room results are initially displayed
    When the user applies extreme filters that are known to return zero results (e.g., "Price: $10000+", "Amenities: NonExistentAmenity")
    Then a "No Results Found" message should be displayed
    And the message should be clear and informative (e.g., "No rooms match your search criteria. Please adjust your filters.")

  Scenario: Verify that pagination is disabled when the search returns 1 or 2 results

    Given the user is on the Room Results page
    And the search returns 1 result
    When the room results are displayed
    Then the pagination should not be visible or be disabled

  @NotToBeAutomated
  Scenario: Verify Room Results Page Responsiveness on Mobile, Tablet, and Laptop Devices
    Given the user is on the Room Results page
    When the page is fully loaded on various devices:
      | Device    | Viewport    |
      | Mobile S  | 320x568     |
      | Mobile M  | 375x667     |
      | Mobile L  | 414x896     |
      | Tablet    | 768x1024    |
      | Laptop    | 1366x768    |
    Then the page layout should adapt correctly, including:
      | Element           | Mobile Responsiveness Verification |
      | Navigation Menu   | Collapsed or hamburger menu |
      | Search Form       | Stacked input fields |
      | Room Cards        | Single column layout |
      | Filters           | Collapsed or modal display |
      | Sorting Options   | Collapsed or modal display |
      | Pagination        | Simplified or scrollable |

  @NotToBeAutomated
  Scenario: Verify Room Results Page Responsiveness on Mobile, Tablet, and Laptop Devices
    Given the user is on the Room Results page
    When the page is fully loaded on various devices:
      | Device    | Viewport    |
      | Mobile S  | 320x568     |
      | Mobile M  | 375x667     |
      | Mobile L  | 414x896     |
      | Tablet    | 768x1024    |
      | Laptop    | 1366x768    |
    Then the page layout should adapt correctly, including:
      | Element           | Mobile Responsiveness Verification |
      | Navigation Menu   | Collapsed or hamburger menu |
      | Search Form       | Stacked input fields |
      | Room Cards        | Single column layout |
      | Filters           | Collapsed or modal display |
      | Sorting Options   | Collapsed or modal display |
      | Pagination        | Simplified or scrollable |

  @NotToBeAutomated
  Scenario Outline: Verify Room Results Page Responsiveness on Different Operating Systems and Devices
    Given the user is on the Room Results page
    When the page is fully loaded on the "<OS>" operating system using a "<Device>" with viewport "<Viewport>"
    Then the page layout should adapt correctly, including:
      | Element           | Mobile Responsiveness Verification |
      | Navigation Menu   | Collapsed or hamburger menu |
      | Search Form       | Stacked input fields |
      | Room Cards        | Single column layout |
      | Filters           | Collapsed or modal display |
      | Sorting Options   | Collapsed or modal display |
      | Pagination        | Simplified or scrollable |

    Examples:
      | OS        | Device          | Viewport   |
      | Android   | Google Pixel 5  | 393x851    |
      | Android   | Samsung Galaxy S21 | 360x800  |
      | iOS       | iPhone 12       | 390x844    |
      | iOS       | iPhone SE (2nd gen) | 375x667 |
      | Android   | Tablet          | 800x1280   |
      | iOS       | iPad            | 768x1024   |









