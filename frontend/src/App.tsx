import { Routes, Route } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import MenuSlider from '@/components/common/MenuSlider/MenuSlider';
import SnackBar from '@/components/common/SnackBar/SnackBar';
import ErrorBoundary from '@/components/helper/ErrorBoundary';
import PrivateRouter from '@/components/helper/PrivateRouter';
import PublicRouter from '@/components/helper/PublicRouter';
import Header from '@/components/layout/Header/Header';
import TabBar from '@/components/layout/TabBar/TabBar';
import { URL } from '@/constants/url';
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
	return (
		<Layout>
			<Header />
			<ErrorBoundary enable={false}>
				<Content>
					<Routes>
						<Route path={URL.LOGIN_CONTROLLER} element={<LoginController />} />
						<Route path={URL.REFRESH_TOKEN_HANDLER} element={<RefreshTokenHandler />} />
						<Route path={URL.CATEGORY_SELECTOR} element={<CategorySelector />} />
						<Route element={<PrivateRouter isAuthenticated={isLogin} />}>
							<Route path={URL.WRITING_ARTICLE} element={<WritingArticles />} />
							<Route path={URL.VOTE_GENERATOR} element={<VoteGenerator />} />
							<Route path={URL.MY_PAGE} element={<MyPage />} />
							<Route path={URL.VOTE_DEADLINE_GENERATOR} element={<VoteDeadlineGenerator />} />
						</Route>
						<Route element={<PublicRouter isAuthenticated={isLogin} />}>
							<Route path={URL.LOGIN} element={<Login />} />
						</Route>
						<Route path={URL.CATEGORY_ARTICLE} element={<CategoryArticles />} />
						<Route path={URL.QUESTION_DETAIL} element={<ErrorDetail />} />
						<Route path={URL.DISCUSSION_DETAIL} element={<DiscussionDetail />} />
						<Route path={URL.MODIFY_ARTICLE} element={<UpdateWriting />} />
						<Route path={URL.SEARCH_RESULT} element={<Search />} />
						<Route path={URL.HASH_TAG_SEARCH} element={<HashTagSearch />} />
						<Route path={URL.INQUIRE} element={<InquirePage />} />
						<Route path={URL.NOT_FOUND} element={<NotFound />} />
						<Route path={URL.HOME} element={<Home />} />
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
