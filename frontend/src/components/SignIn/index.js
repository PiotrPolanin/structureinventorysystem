import {Container, SigInFormWrap, Icon, SignFormContent, Form, FormH1, FormLabel, FormInput, FormButton, Text} from '../SignIn/SignInElements';

const SignIn = () => {
  return (
    <>
      <Container>
        <SigInFormWrap>
          <Icon to="/">SIS</Icon>
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
      </Container>
    </>
  );
};

export default SignIn;
