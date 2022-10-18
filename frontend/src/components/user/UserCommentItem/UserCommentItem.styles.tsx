import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;

	width: 90%;

	${({ theme }) => css`
		height: ${theme.size.SIZE_090};

		border: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};
		border-radius: ${theme.size.SIZE_004};

		background-color: ${theme.colors.GRAY_100};

		padding: ${theme.size.SIZE_002};

		gap: ${theme.size.SIZE_010};

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: 80%;
			justify-content: center;
		}
	`}
`;

export const ArticleBox = styled.div`
	display: flex;
	align-items: center;
`;

export const ArticleTitle = styled.div`
	width: 80%;
	margin-left: ${({ theme }) => theme.size.SIZE_008};
	overflow: hidden;
	word-break: break-all;
	white-space: nowrap;
	text-overflow: ellipsis;
`;

export const ArticleCategory = styled.div<{ isQuestion: boolean }>`
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

export const ContentLabel = styled.span`
	margin-right: ${({ theme }) => theme.size.SIZE_010};
`;

export const ContentBox = styled.div`
	overflow: hidden;
	word-break: break-all;
	white-space: wrap;

	padding: ${({ theme }) => theme.size.SIZE_002};
`;

export const CommentTime = styled.div`
	width: 100%;
	text-align: right;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_012};
		color: ${theme.colors.BLACK_400};
	`}
`;
