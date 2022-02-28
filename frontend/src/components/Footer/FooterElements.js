import styled from "styled-components";

export const FooterContainer = styled.div`
  background: #2f4f4f;
  height: 80px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index=0;

  @media screen and (max-width: 960px) {
    transition: 0.8 all ease;
  }
`;

export const WebsiteRights = styled.small`
  color: #fff;
  margin-bottom: 16px;
`;
