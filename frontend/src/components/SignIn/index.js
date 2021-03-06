import {
  SigInFormWrap,
  SignFormContent,
  Form,
  FormH1,
  FormLabel,
  FormInput,
  FormButton,
  Text,
} from "../SignIn/SignInElements";

const SignIn = () => {
  return (
    <SigInFormWrap>
      <SignFormContent>
        <Form action="#">
          <FormH1>Sign in to your account</FormH1>
          <FormLabel htmlFor="for">Email</FormLabel>
          <FormInput type="email" required />
          <FormLabel htmlFor="for">Password</FormLabel>
          <FormInput type="password" required />
          <FormButton type="submit">Sign in</FormButton>
          <Text>Forgot password?</Text>
        </Form>
      </SignFormContent>
    </SigInFormWrap>
  );
};

export default SignIn;
