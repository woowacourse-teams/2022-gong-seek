import React, { Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import Loading from '@/components/@common/Loading/Loading';
import SnackBar from '@/components/@common/SnackBar/SnackBar';
import PrivateRouter from '@/components/@helper/router/PrivateRouter';
import PublicRouter from '@/components/@helper/router/PublicRouter';
import Header from '@/components/@layout/Header/Header';
import TabBar from '@/components/@layout/TabBar/TabBar';
import { URL } from '@/constants/url';
import useHandleHeaderByScroll from '@/hooks/common/useHandleHeaderByScroll';
import usePageChange from '@/hooks/common/usePageChange';
import { dropdownState } from '@/store/dropdownState';
import { getUserIsLogin } from '@/store/userState';
import styled from '@emotion/styled';

const Home = React.lazy(() => import('@/pages/Home'));
const CategoryArticles = React.lazy(() => import('@/pages/CategoryArticles/CategoryArticles'));
const CategorySelector = React.lazy(() => import('@/pages/CategorySelector/CategorySelector'));
const DiscussionDetail = React.lazy(() => import('@/pages/DiscussionDetail'));
const QuestionDetail = React.lazy(() => import('@/pages/QuestionDetail'));
const HashTagSearch = React.lazy(() => import('@/pages/HashTagSearch'));
const InquirePage = React.lazy(() => import('@/pages/Inquire'));
const Login = React.lazy(() => import('@/pages/Login'));
const LoginHandler = React.lazy(() => import('@/components/@helper/accessHandler/LoginHandler'));
const RefreshTokenHandler = React.lazy(
	() => import('@/components/@helper/accessHandler/RefreshTokenHandler'),
);
const MyPage = React.lazy(() => import('@/pages/MyPage'));
const NotFound = React.lazy(() => import('@/pages/NotFound'));
const Search = React.lazy(() => import('@/pages/Search'));
const UpdateWriting = React.lazy(() => import('@/pages/UpdateWriting'));
const VoteDeadlineGenerator = React.lazy(() => import('@/pages/VoteDeadlineGenerator'));
const VoteGenerator = React.lazy(() => import('@/pages/VoteGenerator'));
const WritingArticles = React.lazy(() => import('@/pages/WritingArticles'));
const TemporaryArticles = React.lazy(() => import('@/pages/TemporaryArticles'));
const WritingTempArticle = React.lazy(() => import('@/pages/WritingTempArticle'));

const Layout = styled.div`
	position: relative;
	height: 100vh;
	width: 100vw;
	max-width: 1500px;
	margin: 0 auto;
`;

const Content = styled.main`
	width: 100%;
	min-height: calc(100vh - 2 * ${({ theme }) => theme.size.SIZE_126});
	padding-bottom: 7rem;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		width: calc(100% - ${({ theme }) => theme.size.SIZE_060} * 2);
		padding: 1rem 5rem;
		justify-content: space-between;
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_MIDDLE}) {
		width: calc(100% - ${({ theme }) => theme.size.SIZE_110} * 2);
		padding: 1rem 7rem;
		justify-content: space-between;
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		width: calc(100% - ${({ theme }) => theme.size.SIZE_160} * 2);
		padding: 1rem ${({ theme }) => theme.size.SIZE_160};
		justify-content: space-between;
	}
`;

const App = () => {
	const isLogin = useRecoilValue(getUserIsLogin);
	const [dropdown, setDropdown] = useRecoilState(dropdownState);
	const { setIsActiveHeader } = useHandleHeaderByScroll();

	const handleChangePage = () => {
		setIsActiveHeader(true);
	};

	usePageChange(handleChangePage);

	const handleClickLayout = () => {
		dropdown.isOpen && setDropdown({ isOpen: false });
	};

	return (
		<Layout onClick={handleClickLayout}>
			<Header />
			<Content>
				<Suspense fallback={<Loading />}>
					<Routes>
						<Route path={URL.LOGIN_CONTROLLER} element={<LoginHandler />} />
						<Route path={URL.CATEGORY_SELECTOR} element={<CategorySelector />} />
						<Route element={<PrivateRouter isAuthenticated={isLogin} />}>
							<Route path={URL.WRITING_ARTICLE} element={<WritingArticles />} />
							<Route path={URL.VOTE_GENERATOR} element={<VoteGenerator />} />
							<Route path={URL.MY_PAGE} element={<MyPage />} />
							<Route path={URL.VOTE_DEADLINE_GENERATOR} element={<VoteDeadlineGenerator />} />
							<Route path={URL.TEMP_ARTICLE_LIST} element={<TemporaryArticles />} />
							<Route path={URL.UPDATE_TEMP_ARTICLE} element={<WritingTempArticle />} />
							<Route path={URL.REFRESH_TOKEN_HANDLER} element={<RefreshTokenHandler />} />
						</Route>
						<Route element={<PublicRouter isAuthenticated={isLogin} />}>
							<Route path={URL.LOGIN} element={<Login />} />
						</Route>
						<Route path={URL.CATEGORY_ARTICLE} element={<CategoryArticles />} />
						<Route path={URL.QUESTION_DETAIL} element={<QuestionDetail />} />
						<Route path={URL.DISCUSSION_DETAIL} element={<DiscussionDetail />} />
						<Route path={URL.MODIFY_ARTICLE} element={<UpdateWriting />} />
						<Route path={URL.SEARCH_RESULT} element={<Search />} />
						<Route path={URL.HASH_TAG_SEARCH} element={<HashTagSearch />} />
						<Route path={URL.INQUIRE} element={<InquirePage />} />
						<Route path={URL.NOT_FOUND} element={<NotFound />} />
						<Route path={URL.HOME} element={<Home />} />
					</Routes>
				</Suspense>
			</Content>
			<TabBar />
			<SnackBar />
		</Layout>
	);
};

export default App;
