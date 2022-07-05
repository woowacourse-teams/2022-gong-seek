import styled from '@emotion/styled';
import SearchBar from '@/components/common/SearchBar/SearchBar';
import { Link } from 'react-router-dom';

const HeaderSection = styled.header`
	position: fixed;
	top: 0;
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-around;
	padding-top: 1rem;
	width: 100%;
`;
const LogoLink = styled.h1`
	font-weight: 800;
	color: ${({ theme }) => theme.colors.PURPLE_500};
	font-size: ${({ theme }) => theme.fonts.SIZE_022};
`;

const SearchBarBox = styled.div`
	width: 60%;
`;

const StyledLink = styled(Link)`
	text-decoration: none;
`;

const Header = () => (
	<HeaderSection>
		<StyledLink to="/">
			<LogoLink>공식</LogoLink>
		</StyledLink>
		<SearchBarBox>
			<SearchBar />
		</SearchBarBox>
	</HeaderSection>
);

export default Header;
