import { AiOutlineSearch } from 'react-icons/ai';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	width: 100%;

	align-items: center;

	justify-content: space-between;
`;

export const SearchBarBox = styled.form<{ isSearchOpen: boolean }>`
	display: flex;

	height: fit-content;

	justify-content: center;
	align-items: center;

	${({ theme, isSearchOpen }) => css`
		width: ${isSearchOpen ? '75%' : '100%'};

		border-radius: ${theme.size.SIZE_016}
		border: ${theme.size.SIZE_001} solid transparent;

		background-color: ${theme.colors.GRAY_500};

		&:active,
		&:hover {
			border: ${theme.size.SIZE_001} solid ${theme.colors.PURPLE_500};
		}

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: 80%;
		}
	`}
`;

export const SearchInput = styled.input`
	width: 80%;

	border-style: none;

	background-color: transparent;

	padding: ${({ theme }) => theme.size.SIZE_008} 0;

	&:focus {
		outline: none;
	}
`;

export const SearchButtonBox = styled.button`
	border-style: none;
	background-color: transparent;
`;

export const SearchButton = styled(AiOutlineSearch)`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_024};

		color: ${theme.colors.PURPLE_500};

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_400};
		}

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			font-size: ${theme.size.SIZE_030};
		}
	`}
`;
