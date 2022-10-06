import Card from '@/components/common/Card/Card';
import Loading from '@/components/common/Loading/Loading';
import { mobileTitleSecondary } from '@/constants/titleType';
import useGetLoginURL from '@/hooks/login/useGetLoginURL';
import LoginButton from '@/pages/Login/LoginButton/LoginButton';
import * as S from '@/pages/Login/index.styles';
import { LoginCardStyle } from '@/styles/cardStyle';

const Login = () => {
	const { isLoading, handleLoginButtonClick } = useGetLoginURL();

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<Card {...LoginCardStyle}>
				<h2 css={mobileTitleSecondary}>로그인</h2>
				<LoginButton loginType="github" onClick={handleLoginButtonClick}>
					github로 로그인하기
				</LoginButton>
			</Card>
		</S.Container>
	);
};

export default Login;
