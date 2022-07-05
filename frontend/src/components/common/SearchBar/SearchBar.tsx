import styled from '@emotion/styled';
import { useState } from 'react';
import { BiSearchAlt } from 'react-icons/bi';

export const Container = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%;
	height: fit-content;
	background-color: ${({ theme }) => theme.colors.GRAY_500};
	border-radius: 1rem;
	border: ${({ theme }) => theme.fonts.SIZE_001} solid transparent;
	&:active,
	&:hover {
		border: ${({ theme }) => theme.fonts.SIZE_001} solid ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

export const SearchInput = styled.input`
	border-style: none;
	padding: 0.5rem 0;
	width: 80%;
	background-color: transparent;
	&:focus {
		outline: none;
	}
`;

export const SearchButton = styled(BiSearchAlt)`
	font-size: ${({ theme }) => theme.fonts.SIZE_024};
	color: ${({ theme }) => theme.colors.PURPLE_500};

	&:hover {
		color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;

const SearchBar = () => {
	const [search, setSearch] = useState('');
	return (
		<Container>
			<SearchInput type="text" />
			<SearchButton />
		</Container>
	);
};

export default SearchBar;
