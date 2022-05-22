package tk.quasar.unhtools.parser.models;

public enum QuestionType {
  TRUE_FALSE,
  MULTIPLE_CHOICE,
  MULTIPLE_ANSWER,
  FORMULA_MATCHING,
  FREE_FORM,
  ;

  public static QuestionType getQuestionType(Item item) {
    return item.getPresentation()
        .getMultipleChoiceResponse()
        .map(QuestionType::getQuestionType)
        .orElse(FREE_FORM);
  }

  private static QuestionType getQuestionType(MultipleChoiceResponse response) {
    if (response.getCardinality().equalsIgnoreCase("single")) {
      if (isTrueFalse(response.getChoice())) {
        return TRUE_FALSE;
      } else if (isFormulaMatching(response.getChoice())) {
        return FORMULA_MATCHING;
      } else {
        return MULTIPLE_CHOICE;
      }
    } else {
      return MULTIPLE_ANSWER;
    }
  }

  private static boolean isFormulaMatching(MultipleChoice choice) {
    return choice.getChoices()
        .stream()
        .allMatch(c -> {
          MaterialText text = c.getMaterial().getText();
          return text.getType().equalsIgnoreCase("text/html")
              && text.getContent().startsWith("<img class=\"equation_image\"");
        });
  }

  private static boolean isTrueFalse(MultipleChoice choice) {
    if (choice.getChoices().size() != 2) {
      return false;
    }

    Choice c1 = choice.getChoices().get(0);
    Choice c2 = choice.getChoices().get(1);
    if (isTrue(c1)) {
      return isFalse(c2);
    } else if (isFalse(c1)) {
      return isTrue(c2);
    } else {
      return false;
    }
  }

  private static boolean isTrue(Choice choice) {
    MaterialText text = choice.getMaterial().getText();
    return text.getType().equalsIgnoreCase("text/plain")
        && text.getContent().equalsIgnoreCase("true");
  }

  private static boolean isFalse(Choice choice) {
    MaterialText text = choice.getMaterial().getText();
    return text.getType().equalsIgnoreCase("text/plain")
        && text.getContent().equalsIgnoreCase("false");
  }
}
