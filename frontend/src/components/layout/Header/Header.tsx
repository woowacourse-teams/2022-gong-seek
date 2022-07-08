import SearchBar from '@/components/common/SearchBar/SearchBar';
import * as S from '@/components/layout/Header/Header.style';

const Header = () => (
	<S.HeaderSection>
		<S.StyledLink to="/">
			<S.LogoLink>공식</S.LogoLink>
		</S.StyledLink>
		<S.SearchBarBox>
			<SearchBar />
		</S.SearchBarBox>
	</S.HeaderSection>
);

export default Header;
