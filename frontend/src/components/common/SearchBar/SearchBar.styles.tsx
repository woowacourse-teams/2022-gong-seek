import { AiOutlineSearch } from 'react-icons/ai';

import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	width: 100%;

	align-items: center;

	justify-content: space-between;
`;

export const SearchBarBox = styled.form<{ isSearchOpen: boolean }>`
	display: flex;

	justify-content: center;
	align-items: center;

	width: ${(props) => (props.isSearchOpen ? '75%' : '100%')};
	height: fit-content;

	border-radius: 1rem;
	border: ${({ theme }) => theme.size.SIZE_001} solid transparent;

	background-color: ${({ theme }) => theme.colors.GRAY_500};

	&:active,
	&:hover {
		border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.PURPLE_500};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 80%;
	}
`;

export const SearchInput = styled.input`
	width: 80%;

	border-style: none;

	background-color: transparent;

	padding: 0.5rem 0;

	&:focus {
		outline: none;
	}
`;

export const SearchButtonBox = styled.button`
	border-style: none;
	background-color: transparent;
`;

export const SearchButton = styled(AiOutlineSearch)`
	font-size: ${({ theme }) => theme.size.SIZE_024};

	color: ${({ theme }) => theme.colors.PURPLE_500};

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_400};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		font-size: ${({ theme }) => theme.size.SIZE_030};
	}
`;
