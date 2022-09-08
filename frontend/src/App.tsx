import React, { Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import Loading from '@/components/common/Loading/Loading';
import MenuSlider from '@/components/common/MenuSlider/MenuSlider';
import SnackBar from '@/components/common/SnackBar/SnackBar';
import ErrorBoundary from '@/components/helper/ErrorBoundary';
import PrivateRouter from '@/components/helper/PrivateRouter';
import PublicRouter from '@/components/helper/PublicRouter';
import Header from '@/components/layout/Header/Header';
import TabBar from '@/components/layout/TabBar/TabBar';
import Home from '@/pages/Home';
import { dropdownState } from '@/store/dropdownState';
import { menuSliderState } from '@/store/menuSliderState';
import { getUserIsLogin } from '@/store/userState';
import styled from '@emotion/styled';

const CategoryArticles = React.lazy(() => import('@/pages/CategoryArticles/CategoryArticles'));
const CategorySelector = React.lazy(() => import('@/pages/CategorySelector/CategorySelector'));
const DiscussionDetail = React.lazy(() => import('@/pages/DiscussionDetail'));
const ErrorDetail = React.lazy(() => import('@/pages/ErrorDetail'));
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
`;

const Content = styled.main`
	width: 100%;
	min-height: calc(100vh - 2 * ${({ theme }) => theme.size.SIZE_126});
	padding-bottom: 7rem;

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
			<ErrorBoundary enable={false}>
				<Content>
					<Suspense fallback={<Loading />}>
						<Routes>
							<Route path="/callback" element={<LoginController />} />
							<Route path="/check-login" element={<RefreshTokenHandler />} />
							<Route path="/category" element={<CategorySelector />} />
							<Route element={<PrivateRouter isAuthenticated={isLogin} />}>
								<Route path="/article/:category" element={<WritingArticles />} />
								<Route path="/votes/:articleId" element={<VoteGenerator />} />
								<Route path="/my-page" element={<MyPage />} />
								<Route path="/votes-deadline" element={<VoteDeadlineGenerator />} />
							</Route>
							<Route element={<PublicRouter isAuthenticated={isLogin} />}>
								<Route path="/login" element={<Login />} />
							</Route>
							<Route path="/articles/:category" element={<CategoryArticles />} />
							<Route path="/articles/question/:id" element={<ErrorDetail />} />
							<Route path="/articles/discussion/:id" element={<DiscussionDetail />} />
							<Route path="/articles/modify/:category/:id" element={<UpdateWriting />} />
							<Route path="/search-result" element={<Search />} />
							<Route path="/hash-tag" element={<HashTagSearch />} />
							<Route path="/inquire" element={<InquirePage />} />
							<Route path="/*" element={<NotFound />} />
							<Route path="/" element={<Home />} />
						</Routes>
					</Suspense>
				</Content>
			</ErrorBoundary>
			<TabBar />
			<SnackBar />
			{sliderState.isOpen && <Dimmer onClick={() => setSliderState({ isOpen: false })} />}
			{sliderState.isOpen && <MenuSlider closeSlider={() => setSliderState({ isOpen: false })} />}
		</Layout>
	);
};

export default App;
