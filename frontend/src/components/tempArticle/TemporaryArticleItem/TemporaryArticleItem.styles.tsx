import { OneLineTextOverFlow } from '@/styles/mixin';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 80%;
	height: ${({ theme }) => theme.size.SIZE_050};

	${({ theme }) => css`
		height: ${theme.size.SIZE_050};

		border: ${theme.size.SIZE_001} solid ${theme.colors.BLACK_200};
		border-radius: ${theme.size.SIZE_004};

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: 80%;
		}
	`}
`;

export const Title = styled.h2`
	display: block;
	width: 90%;
	${OneLineTextOverFlow}

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_016};
		border-bottom: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};

		padding: ${theme.size.SIZE_004} 0 ${theme.size.SIZE_014} ${theme.size.SIZE_004};
	`}
`;

export const CreatedAt = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_010};
`;

export const SubInfo = styled.div`
	display: flex;
	align-items: center;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_010};
		padding: ${theme.size.SIZE_002};
	`}
`;

export const Category = styled.div<{ isQuestion: boolean }>`
	${({ theme, isQuestion }) => css`
		font-size: ${theme.size.SIZE_012};
		background-color: ${isQuestion ? `${theme.colors.RED_500}` : `${theme.colors.BLUE_500}`};
		padding: ${theme.size.SIZE_004};
		border-radius: ${theme.size.SIZE_004};
	`}
`;
