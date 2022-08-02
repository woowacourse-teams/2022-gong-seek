import { Routes, Route } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import Header from '@/components/layout/Header/Header';
import TabBar from '@/components/layout/TabBar/TabBar';
import PrivateRouter from '@/components/router/PrivateRouter';
import PublicRouter from '@/components/router/PublicRouter';
import CategoryArticles from '@/pages/CategoryArticles/CategoryArticles';
import CategorySelector from '@/pages/CategorySelector/CategorySelector';
import ErrorDetail from '@/pages/ErrorDetail';
import Home from '@/pages/Home';
import Login from '@/pages/Login';
import LoginController from '@/pages/Login/LoginController/LoginController';
import NotFound from '@/pages/NotFound';
import UpdateWriting from '@/pages/UpdateWriting';
import VoteGenerator from '@/pages/VoteGenerator';
import WritingArticles from '@/pages/WritingArticles';
import DiscussionDetail from '@/pages/DiscussionDetail';

import { getUserIsLogin } from '@/store/userState';

import SnackBar from '@/components/common/SnackBar/SnackBar';

import styled from '@emotion/styled';

const Layout = styled.div`
	position: relative;
	height: 100vh;
	width: 100vw;
`;

const Content = styled.main`
	width: 100%;
	padding-bottom: 7rem;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: calc(100% - ${({ theme }) => theme.size.SIZE_160} * 2);
		padding: 1rem ${({ theme }) => theme.size.SIZE_160};
		justify-content: space-between;
	}
`;

const App = () => {
	const isLogin = useRecoilValue(getUserIsLogin);
	return (
		<Layout>
			<Header />
			<Content>
				<Routes>
					<Route path="/callback" element={<LoginController />} />
					<Route path="/category" element={<CategorySelector />} />
					<Route element={<PrivateRouter isAuthenticated={isLogin} />}>
						<Route path="/article/:category" element={<WritingArticles />} />
						<Route path="/votes/:articleId" element={<VoteGenerator />} />
					</Route>
					<Route element={<PublicRouter isAuthenticated={isLogin} />}>
						<Route path="/login" element={<Login />} />
					</Route>
					<Route path="/articles/question/:id" element={<ErrorDetail />} />
					<Route path="/articles/:category" element={<CategoryArticles />} />
					<Route path="/articles/modify/:category/:id" element={<UpdateWriting />} />
					<Route
						path="/articles/discussion/:id"
						element={<DiscussionDetail />}
					/>
					<Route path="/" element={<Home />} />
					<Route path="/*" element={<NotFound />} />
				</Routes>
			</Content>
			<TabBar />
			<SnackBar />
		</Layout>
	);
};

export default App;
