import styled from '@emotion/styled';
import { Routes, Route } from 'react-router-dom';
import Header from '@/components/layout/Header/Header';
import TabBar from '@/components/layout/TabBar/TabBar';
import Login from '@/pages/Login';
import WritingArticles from '@/pages/WritingArticles';
import CategorySelector from '@/pages/CategorySelector/CategorySelector';

import LoginController from '@/pages/Login/LoginController/LoginController';
import Home from '@/pages/Home';
import PrivateRouter from '@/components/router/PrivateRouter';
import PublicRouter from '@/components/router/PublicRouter';
import VoteGenerator from '@/pages/VoteGenerator';
import ErrorDetail from '@/pages/ErrorDetail/index';
import UpdateWriting from '@/pages/UpdateWriting';
import CategoryArticles from '@/pages/CategoryArticles/CategoryArticles';

const Layout = styled.div`
	position: relative;
	height: 100vh;
	width: 100vw;
`;

const Content = styled.main`
	width: 100%;
	padding-bottom: 7rem;
`;

const App = () => {
	const isLogin = !!localStorage.getItem('accessToken');
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
					<Route path="articles/:category" element={<CategoryArticles />} />
					<Route path="/articles/modify/:category/:id" element={<UpdateWriting />} />
					<Route path="articles/:category" element={<CategoryArticles />} />
					{/* <Route
						path="/articles/discussion/:id"
						element={
							// <Detail>
							// 	<Vote articleId="4" />
							// </Detail>
						}
					/> */}
					<Route path="/" element={<Home />} />
				</Routes>
			</Content>
			<TabBar />
		</Layout>
	);
};

export default App;
