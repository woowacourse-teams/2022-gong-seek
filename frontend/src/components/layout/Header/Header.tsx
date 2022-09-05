import { Link } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import SearchBar from '@/components/common/SearchBar/SearchBar';
import * as S from '@/components/layout/Header/Header.styles';
import { URL } from '@/constants/url';
import { searchState } from '@/store/searchState';
import { getUserIsLogin } from '@/store/userState';

const Header = () => {
	const isLogin = useRecoilValue(getUserIsLogin);
	const { isSearchOpen } = useRecoilValue(searchState);

	const onLogOutClick = () => {
		if (window.confirm('정말로 로그아웃을 하시겠습니까?')) {
			localStorage.removeItem('accessToken');
			window.location.href = URL.HOME;
			return;
		}
	};

	if (isSearchOpen) {
		return (
			<S.Container>
				<S.HeaderSection>
					<S.StyledLink to={URL.HOME}>
						<S.LogoImage src={gongseek} />
					</S.StyledLink>
					<S.SearchOpenBox>
						<Link to={URL.SEARCH_RESULT}>
							<SearchBar isValid={false} />
						</Link>
					</S.SearchOpenBox>
				</S.HeaderSection>
			</S.Container>
		);
	}
	return (
		<S.Container>
			<S.HeaderSection>
				<S.StyledLink to={URL.HOME}>
					<S.LogoLink>공식</S.LogoLink>
				</S.StyledLink>
				<S.SearchBarBox>
					<Link to={URL.SEARCH_RESULT}>
						<SearchBar isValid={true} />
					</Link>
				</S.SearchBarBox>
			</S.HeaderSection>
			<S.NavBar>
				{isLogin ? (
					<S.LogOutItem onClick={onLogOutClick}>로그아웃</S.LogOutItem>
				) : (
					<S.NavBarItem to={URL.LOGIN}>로그인</S.NavBarItem>
				)}
				<S.NavBarItem to={URL.CATEGORY_SELECTOR}>글 쓰러 가기</S.NavBarItem>
				<S.NavBarItem to="/articles/question">질문 카테고리</S.NavBarItem>
				<S.NavBarItem to="/articles/discussion">토론 카테고리</S.NavBarItem>
				<S.NavBarItem to={URL.HASH_TAG_SEARCH}>해시태그로 검색하기</S.NavBarItem>
				{isLogin && <S.NavBarItem to={URL.MY_PAGE}>마이페이지</S.NavBarItem>}
				<S.NavBarItem to={URL.INQUIRE}>문의하기</S.NavBarItem>
			</S.NavBar>
		</S.Container>
	);
};

export default Header;
