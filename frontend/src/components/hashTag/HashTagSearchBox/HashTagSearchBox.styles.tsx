import { AiOutlineSearch, AiOutlineDown, AiOutlineUp } from 'react-icons/ai';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.section`
	width: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
`;

export const SearchBarBox = styled.form`
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

export const SearchButtonBox = styled.button`
	border: none;
	background: transparent;
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

	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const HashTagItem = styled.button<{ isChecked: boolean }>`
	width: fit-content;
	height: fit-content;

	background-color: transparent;

	&:hover,
	&:active {
		cursor: pointer;
	}

	${({ theme, isChecked }) => css`
		border: ${theme.size.SIZE_001} solid;
		border-color: ${isChecked ? theme.colors.PURPLE_500 : theme.colors.BLACK_400};
		border-radius: ${theme.size.SIZE_004};

		padding: ${theme.size.SIZE_004};

		background-color: ${isChecked ? theme.colors.PURPLE_500 : 'transparent'};

		color: ${isChecked ? theme.colors.WHITE : theme.colors.BLACK_500};
	`}
`;

export const HashTagButton = styled.button`
	width: fit-content;
	height: fit-content;

	background-color: transparent;

	&:hover,
	&:active {
		cursor: pointer;
	}

	${({ theme }) => css`
		border: ${theme.size.SIZE_001} solid ${theme.colors.BLACK_400};
		border-radius: ${theme.size.SIZE_004};
		padding: ${theme.size.SIZE_004};
	`}
`;

export const EmptyMsg = styled.div``;

export const OpenButton = styled(AiOutlineDown)`
	border: none;
	background-color: transparent;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_024};
		margin-left: ${theme.size.SIZE_010};

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
			cursor: pointer;
		}
	`}
`;

export const CloseButton = styled(AiOutlineUp)`
	border: none;
	background-color: transparent;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_024};
		margin-left: ${theme.size.SIZE_010};

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
			cursor: pointer;
		}
	`}
`;
