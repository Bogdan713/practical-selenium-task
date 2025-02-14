# main feature
Feature: Email functionality

  Background:
    Given the browser is initialized
    And the user is logged into Yahoo Mail

  Scenario Outline: Create a draft email
    When the user creates a draft email to "<receiver>" with subject "<subject>" and body "<body>"
    Then the draft email with subject "<subject>" should be present in the Drafts folder

    Examples:
      | receiver                | subject        | body                  |
      | bogdanprostov@gmail.com | Test Subject 1 | This is a test email. |

  Scenario Outline: Send a draft email
    Given the user creates a draft email to "<receiver>" with subject "<subject>" and body "<body>"
    When the user sends the draft email with subject "<subject>"
    Then the draft email with subject "<subject>" should no longer be present in the Drafts folder
    And the email with subject "<subject>" should be present in the Sent folder

    Examples:
      | receiver                | subject        | body                  |
      | bogdanprostov@gmail.com | Test Subject 2 | This is another test. |