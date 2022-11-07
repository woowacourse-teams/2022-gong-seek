import { Suspense } from 'react';

import Card from '@/components/@common/Card/Card';
import Loading from '@/components/@common/Loading/Loading';
import LoginButton from '@/components/login/LoginButton/LoginButton';
import { mobileTitleSecondary } from '@/constants/titleType';
import useGetLoginURL from '@/hooks/login/useGetLoginURL';
import * as S from '@/pages/Login/index.styles';
import { LoginCardStyle } from '@/styles/cardStyle';

const Login = () => {
	const { handleLoginButtonClick } = useGetLoginURL();

	return (
		<Suspense fallback={<Loading />}>
			<S.Container>
				<Card {...LoginCardStyle}>
					<h2 css={mobileTitleSecondary}>로그인</h2>
					<LoginButton loginType="github" onClick={handleLoginButtonClick}>
						github로 로그인하기
					</LoginButton>
				</Card>
			</S.Container>
		</Suspense>
	);
};

export default Login;
