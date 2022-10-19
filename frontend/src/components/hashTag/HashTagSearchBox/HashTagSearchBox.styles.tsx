import { AiOutlineSearch } from 'react-icons/ai';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.section`
	width: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
`;

export const SearchBarBox = styled.div`
	width: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
`;

export const SearchBar = styled.input`
	width: 70%;
	${({ theme }) => css`
		border: ${theme.size.SIZE_001} solid ${theme.colors.BLACK_300};
		border-radius: ${theme.size.SIZE_004};
		padding: ${theme.size.SIZE_004};
	`}
`;

export const SearchButton = styled(AiOutlineSearch)`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_026};
		margin-left: ${theme.size.SIZE_010};
	`}
	&:hover, &:active {
		cursor: pointer;
	}
`;

export const HashTagListBox = styled.div`
	width: 80%;
	min-height: 10vh;
	${({ theme }) => css`
		margin: ${theme.size.SIZE_020} ${theme.size.SIZE_010};
		border-top: ${theme.size.SIZE_001} solid ${theme.colors.BLACK_300};
		padding: ${theme.size.SIZE_010} ${theme.size.SIZE_014};
	`}
`;

export const HashTagSearchResultDescription = styled.div`
	width: 100%;
	text-align: center;
`;
