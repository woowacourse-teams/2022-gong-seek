import { getGithubURL } from '@/api/login';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import { mobileTitleSecondary } from '@/constants/titleType';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';
import LoginButton from './LoginButton/LoginButton';
import * as S from '@/pages/Login/index.style';

const Login = () => {
	const { data, error, isError, isLoading, isSuccess, refetch } = useQuery(
		'github-url',
		getGithubURL,
		{
			enabled: false,
		},
	);
	const [pageLoading, setPageLoading] = useState(false);

	const handleLoginButtonClick = () => {
		refetch();
	};

	useEffect(() => {
		if (isSuccess) {
			setPageLoading(true);
			window.location.href = data;
		}
	}, [isSuccess]);

	if (isLoading || pageLoading) return <div>로딩중...</div>;

	if (isError) {
		if (error instanceof Error) {
			return <div>{error.message}</div>;
		}
		return null;
	}

	return (
		<S.Container>
			<PageLayout
				width="80%"
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
