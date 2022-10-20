import { AiOutlineDown, AiOutlineUp } from 'react-icons/ai';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;
	width: 100%;
`;

export const HashTagPreviewBox = styled.div``;

export const HashTagLists = styled.div`
	display: flex;
	flex-wrap: wrap;

	width: 100%;

	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const HashTagItem = styled.button<{ isChecked: boolean }>`
	width: fit-content;
	height: fit-content;
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
