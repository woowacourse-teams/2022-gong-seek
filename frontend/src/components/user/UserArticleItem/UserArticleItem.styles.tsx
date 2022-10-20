import { AiOutlineMessage } from 'react-icons/ai';

import { TextOverflow } from '@/styles/mixin';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	flex-direction: column;

	width: 90%;
	height: fit-content;

	${({ theme }) => css`
		border: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};
		border-radius: ${theme.size.SIZE_004};

		background-color: ${theme.colors.GRAY_100};

		padding: ${theme.size.SIZE_006};

		gap: ${theme.size.SIZE_020};

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: 80%;
			justify-content: center;
		}
	`}
`;

export const CategoryName = styled.div<{ isQuestion: boolean }>`
	width: fit-content;
	height: fit-content;

	${({ theme, isQuestion }) => css`
		color: ${theme.colors.WHITE};

		border: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};
		border-radius: ${theme.size.SIZE_010};

		background-color: ${isQuestion ? `${theme.colors.RED_600}` : `${theme.colors.BLUE_500}`};

		padding: ${theme.size.SIZE_004};
	`}
`;

export const ArticleTitleBox = styled.div`
	display: flex;
	align-items: center;
`;

export const ArticleTitle = styled.div`
	width: 80%;

	${TextOverflow}

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_016};
		margin-left: ${theme.size.SIZE_008};
	`}
`;

export const ArticleSubInfo = styled.div`
	display: flex;
	justify-content: space-between;
`;

export const ArticleTime = styled.div`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_012};
		color: ${theme.colors.BLACK_400};
	`}
`;

export const ArticleRightBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const CommentBox = styled.div`
	display: flex;

	gap: ${({ theme }) => theme.size.SIZE_006};
`;
export const CommentIcon = styled(AiOutlineMessage)`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_020};
		margin-top: -${theme.size.SIZE_006};
	`}
`;
export const CommentCount = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_014};
`;

export const Views = styled.span`
	font-size: ${({ theme }) => theme.size.SIZE_014};
`;
