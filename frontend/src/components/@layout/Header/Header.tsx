import { useRecoilValue } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import * as S from '@/components/@layout/Header/Header.styles';
import SearchBar from '@/components/search/SearchBar/SearchBar';
import UserProfileIcon from '@/components/user/UserProfileIcon/UserProfileIcon';
import { URL } from '@/constants/url';
import useEnterToClick from '@/hooks/common/useEnterToClick';
import useHandleHeaderByScroll from '@/hooks/common/useHandleHeaderByScroll';
import useScroll from '@/hooks/common/useScroll';
import { searchState } from '@/store/searchState';
import { getUserIsLogin } from '@/store/userState';

const Header = () => {
	const isLogin = useRecoilValue(getUserIsLogin);
	const { isSearchOpen } = useRecoilValue(searchState);
	const { handleHeaderViewByScroll, headerElement, isActiveHeader } = useHandleHeaderByScroll();
	const { ref } = useEnterToClick();

	useScroll(handleHeaderViewByScroll);

	if (isSearchOpen) {
		return (
			<S.Container ref={headerElement} active={isActiveHeader}>
				<S.HeaderSection>
					<S.StyledLink to={URL.HOME}>
						<S.LogoImage src={gongseek} />
					</S.StyledLink>
					<S.SearchOpenBox>
						<S.StyledLink to={URL.SEARCH_RESULT} aria-label="검색 결과 페이지로 전환하는 링크">
							<SearchBar isValid={false} />
						</S.StyledLink>
					</S.SearchOpenBox>
				</S.HeaderSection>
			</S.Container>
		);
	}

	return (
		<S.Container ref={headerElement} active={isActiveHeader}>
			<S.HeaderSection>
				<S.StyledLink to={URL.HOME}>
					<S.LogoLink>공식</S.LogoLink>
				</S.StyledLink>
				<S.SearchBarBox>
					<S.StyledLink to={URL.SEARCH_RESULT} aria-label="엔터를 눌러 검색을 진행하세요">
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
					<S.ProfileIconBox ref={ref}>
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
