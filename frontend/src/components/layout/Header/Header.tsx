import { useRecoilValue } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import SearchBar from '@/components/common/SearchBar/SearchBar';
import UserProfileIcon from '@/components/common/UserProfileIcon/UserProfileIcon';
import * as S from '@/components/layout/Header/Header.styles';
import { URL } from '@/constants/url';
import { searchState } from '@/store/searchState';
import { getUserIsLogin } from '@/store/userState';

const Header = () => {
	const isLogin = useRecoilValue(getUserIsLogin);
	const { isSearchOpen } = useRecoilValue(searchState);

	if (isSearchOpen) {
		return (
			<S.Container>
				<S.HeaderSection>
					<S.StyledLink to={URL.HOME}>
						<S.LogoImage src={gongseek} />
					</S.StyledLink>
					<S.SearchOpenBox>
						<S.StyledLink to={URL.SEARCH_RESULT}>
							<SearchBar isValid={false} />
						</S.StyledLink>
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
					<S.StyledLink to={URL.SEARCH_RESULT}>
						<SearchBar isValid={true} />
					</S.StyledLink>
				</S.SearchBarBox>
			</S.HeaderSection>
			<S.NavBar>
				<S.NavBarItemBox>
					<S.NavBarItem to={URL.CATEGORY_SELECTOR}>글 쓰러 가기</S.NavBarItem>
					<S.NavBarItem to={URL.CATEGORY_QUESTION}>질문 게시판</S.NavBarItem>
					<S.NavBarItem to={URL.CATEGORY_DISCUSSION}>토론 게시판</S.NavBarItem>
					<S.NavBarItem to={URL.HASH_TAG_SEARCH}>해시태그</S.NavBarItem>
					<S.NavBarItem to={URL.INQUIRE}>문의하기</S.NavBarItem>
				</S.NavBarItemBox>
				{isLogin ? (
					<S.ProfileIconBox>
						<UserProfileIcon />
					</S.ProfileIconBox>
				) : (
					<S.LoginIn to={URL.LOGIN}>로그인</S.LoginIn>
				)}
			</S.NavBar>
		</S.Container>
	);
};

export default Header;
