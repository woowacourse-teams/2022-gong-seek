import PageLayout from '@/components/layout/PageLayout/PageLayout';
import { mobileTitleSecondary } from '@/constants/titleType';
import styled from '@emotion/styled';
import LoginButton from './LoginButton/LoginButton';

const Container = styled.div`
	display: flex;
	width: 100%;
	height: 100%;
	justify-content: center;
	align-items: center;
`;

const Login = () => (
	<Container>
		<PageLayout
			width="80%"
			height="14rem"
			flexDirection="column"
			justifyContent="space-around"
			padding="1rem"
		>
			<h2 css={mobileTitleSecondary}>로그인</h2>
			<LoginButton
				loginType="github"
				onClick={() => {
					console.log('로그인버튼클릭');
				}}
			>
				github로 로그인하기
			</LoginButton>
		</PageLayout>
	</Container>
);

export default Login;
