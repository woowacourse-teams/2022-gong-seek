import React, { Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import Loading from '@/components/common/Loading/Loading';
import SnackBar from '@/components/common/SnackBar/SnackBar';
import PrivateRouter from '@/components/helper/PrivateRouter';
import PublicRouter from '@/components/helper/PublicRouter';
import Header from '@/components/layout/Header/Header';
import TabBar from '@/components/layout/TabBar/TabBar';
import { URL } from '@/constants/url';
import { dropdownState } from '@/store/dropdownState';
import { menuSliderState } from '@/store/menuSliderState';
import { getUserIsLogin } from '@/store/userState';
import styled from '@emotion/styled';

const MenuSlider = React.lazy(() => import('@/components/common/MenuSlider/MenuSlider'));
const Home = React.lazy(() => import('@/pages/Home'));
const CategoryArticles = React.lazy(() => import('@/pages/CategoryArticles/CategoryArticles'));
const CategorySelector = React.lazy(() => import('@/pages/CategorySelector/CategorySelector'));
const DiscussionDetail = React.lazy(() => import('@/pages/DiscussionDetail'));
const QuestionDetail = React.lazy(() => import('@/pages/QuestionDetail'));
const HashTagSearch = React.lazy(() => import('@/pages/HashTagSearch'));
const InquirePage = React.lazy(() => import('@/pages/Inquire'));
const Login = React.lazy(() => import('@/pages/Login'));
const LoginController = React.lazy(() => import('@/pages/Login/LoginController/LoginController'));
const RefreshTokenHandler = React.lazy(
	() => import('@/pages/Login/RefreshTokenHandler/RefreshTokenHandler'),
);
const MyPage = React.lazy(() => import('@/pages/MyPage'));
const NotFound = React.lazy(() => import('@/pages/NotFound'));
const Search = React.lazy(() => import('@/pages/Search'));
const UpdateWriting = React.lazy(() => import('@/pages/UpdateWriting'));
const VoteDeadlineGenerator = React.lazy(() => import('@/pages/VoteDeadlineGenerator'));
const VoteGenerator = React.lazy(() => import('@/pages/VoteGenerator'));
const WritingArticles = React.lazy(() => import('@/pages/WritingArticles'));

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

	@media (min-width: 700px) {
		width: calc(100% - ${({ theme }) => theme.size.SIZE_060} * 2);
		padding: 1rem 5rem;
		justify-content: space-between;
	}

	@media (min-width: 1000px) {
		width: calc(100% - ${({ theme }) => theme.size.SIZE_110} * 2);
		padding: 1rem 7rem;
		justify-content: space-between;
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: calc(100% - ${({ theme }) => theme.size.SIZE_160} * 2);
		padding: 1rem ${({ theme }) => theme.size.SIZE_160};
		justify-content: space-between;
	}
`;

const Dimmer = styled.div`
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;

	background-color: ${({ theme }) => theme.colors.GRAY_500};

	z-index: ${({ theme }) => theme.zIndex.MENU_SLIDER_BACKGROUND};
`;

const App = () => {
	const isLogin = useRecoilValue(getUserIsLogin);
	const [sliderState, setSliderState] = useRecoilState(menuSliderState);
	const [dropdown, setDropdown] = useRecoilState(dropdownState);

	return (
		<Layout
			onClick={() => {
				dropdown.isOpen && setDropdown({ isOpen: false });
			}}
		>
			<Header />
			<Content>
				<Suspense fallback={<Loading />}>
					<Routes>
						<Route path={URL.LOGIN_CONTROLLER} element={<LoginController />} />
						<Route path={URL.CATEGORY_SELECTOR} element={<CategorySelector />} />
						<Route element={<PrivateRouter isAuthenticated={isLogin} />}>
							<Route path={URL.REFRESH_TOKEN_HANDLER} element={<RefreshTokenHandler />} />
							<Route path={URL.WRITING_ARTICLE} element={<WritingArticles />} />
							<Route path={URL.VOTE_GENERATOR} element={<VoteGenerator />} />
							<Route path={URL.MY_PAGE} element={<MyPage />} />
							<Route path={URL.VOTE_DEADLINE_GENERATOR} element={<VoteDeadlineGenerator />} />
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
			{sliderState.isOpen && <Dimmer onClick={() => setSliderState({ isOpen: false })} />}
			{sliderState.isOpen && (
				<Suspense fallback={<Loading />}>
					<MenuSlider closeSlider={() => setSliderState({ isOpen: false })} />
				</Suspense>
			)}
		</Layout>
	);
};

export default App;
