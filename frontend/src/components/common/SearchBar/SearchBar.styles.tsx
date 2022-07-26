import { BiSearchAlt } from 'react-icons/bi';

import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	justify-content: center;
	align-items: center;

	width: 100%;
	height: fit-content;

	border-radius: 1rem;
	border: ${({ theme }) => theme.size.SIZE_001} solid transparent;

	background-color: ${({ theme }) => theme.colors.GRAY_500};

	&:active,
	&:hover {
		border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.PURPLE_500};
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

export const SearchButton = styled(BiSearchAlt)`
	font-size: ${({ theme }) => theme.size.SIZE_024};

	color: ${({ theme }) => theme.colors.PURPLE_500};

	&:hover {
		color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;
