import { FormMessageParagraph } from "./form_message_elements";
import { FormMessageText } from "./form_message_elements";

const FormMessage = (props) => {
  return (
    <FormMessageParagraph isError={props.isError}>
      <FormMessageText>{props.message}</FormMessageText>
    </FormMessageParagraph>
  );
};

export default FormMessage;
