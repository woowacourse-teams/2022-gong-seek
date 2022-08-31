import { Routes, Route } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import MenuSlider from '@/components/common/MenuSlider/MenuSlider';
import SnackBar from '@/components/common/SnackBar/SnackBar';
import ErrorBoundary from '@/components/helper/ErrorBoundary';
import PrivateRouter from '@/components/helper/PrivateRouter';
import PublicRouter from '@/components/helper/PublicRouter';
import Header from '@/components/layout/Header/Header';
import TabBar from '@/components/layout/TabBar/TabBar';
import CategoryArticles from '@/pages/CategoryArticles/CategoryArticles';
import CategorySelector from '@/pages/CategorySelector/CategorySelector';
import DiscussionDetail from '@/pages/DiscussionDetail';
import ErrorDetail from '@/pages/ErrorDetail';
import HashTagSearch from '@/pages/HashTagSearch';
import Home from '@/pages/Home';
import InquirePage from '@/pages/Inquire';
import Login from '@/pages/Login';
import LoginController from '@/pages/Login/LoginController/LoginController';
import RefreshTokenHandler from '@/pages/Login/RefreshTokenHandler/RefreshTokenHandler';
import MyPage from '@/pages/MyPage';
import NotFound from '@/pages/NotFound';
import Search from '@/pages/Search';
import UpdateWriting from '@/pages/UpdateWriting';
import VoteDeadlineGenerator from '@/pages/VoteDeadlineGenerator';
import VoteGenerator from '@/pages/VoteGenerator';
import WritingArticles from '@/pages/WritingArticles';
import { dropdownState } from '@/store/dropdownState';
import { menuSliderState } from '@/store/menuSliderState';
import { getUserIsLogin } from '@/store/userState';
import styled from '@emotion/styled';

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
