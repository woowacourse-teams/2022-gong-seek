import Loading from '@/components/common/Loading/Loading';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import { mobileTitleSecondary } from '@/constants/titleType';
import useGetLoginURL from '@/hooks/login/useGetLoginURL';
import LoginButton from '@/pages/Login/LoginButton/LoginButton';
import * as S from '@/pages/Login/index.styles';

const Login = () => {
	const { isLoading, handleLoginButtonClick } = useGetLoginURL();

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<PageLayout
				width="80%"
				maxWidth="25rem"
				height="14rem"
				flexDirection="column"
				justifyContent="space-around"
				padding="1rem"
			>
				<h2 css={mobileTitleSecondary}>로그인</h2>
				<LoginButton loginType="github" onClick={handleLoginButtonClick}>
					github로 로그인하기
				</LoginButton>
			</PageLayout>
		</S.Container>
	);
};

export default Login;
