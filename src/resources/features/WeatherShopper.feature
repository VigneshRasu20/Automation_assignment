Feature: Weather Shopper

  @WeatherShopper
  Scenario: Open the Weather Shopper Application
    Given Open the browser and navigate to Weather Shopper URL
    Then User is in Current temperature screen

  @WeatherShopper
  Scenario Outline: Select Moisturizers and Sunscreens based on the temperature
    Given User is in Current temperature screen
    When Based on the temperature select Moisturizers or Sunscreens and add two products to cart
    Then Add products<ProductCount> and click on the Items in cart

    Examples: 
      | ProductCount |
      |            2 |

  @WeatherShopper
  Scenario: Check the products added and click Pay with card
    Given User is in Checkout screen
    And Check the products present in Checkout screen
    Then Click the PaywithCard button

  @WeatherShopper
  Scenario Outline: Enter the card details
    Given The User is in the Stripe.com screen
    And Enter the Email<email> , Card number<cardNo> , Month and year<MMyy> , CVC<cvc> , Zipcode<zip>
    Then Click the pay button

    Examples: 
      | email                  | cardNo           | MMyy | cvc | zip    |
      | vignesh.aaaa@gmail.com | 4242424242424242 | 1250 | 123 | 123456 |

  @WeatherShopper
  Scenario Outline: Verify the Payment status
    Given User is in PAYMENT SUCCESS screen
    And Check the Message<message> is displayed

    Examples: 
      | message                                                                               |
      | Your payment was successful. You should receive a follow-up call from our sales team. |

  @WeatherShopper
  Scenario: Close the opened browser
    And Close the browser
