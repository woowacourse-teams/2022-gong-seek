import { getGithubURL } from '@/api/login';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import { mobileTitleSecondary } from '@/constants/titleType';
import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';
import LoginButton from './LoginButton/LoginButton';

const Container = styled.div`
	display: flex;
	width: 100%;
	height: calc(100vh - 15rem);
	justify-content: center;
	align-items: center;
`;

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
		<Container>
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
		</Container>
	);
};

export default Login;
