import styled from '@emotion/styled';

const PageLayout = styled.section`
	width: 100%;
	height: 200px;
	border-radius: ${({ theme }) => theme.fonts.SIZE_010};
	box-shadow: 0px 8px 24px ${({ theme }) => theme.boxShadows.secondary};
`;

export default PageLayout;
