import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100%;
`;

export const Title = styled.h2`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_018};
		color: ${theme.colors.PURPLE_500};

		margin-bottom: ${theme.size.SIZE_020};
		padding-left: ${theme.size.SIZE_010};
	`}
`;

export const ContentContainer = styled.div`
	display: flex;
	flex-direction: column;

	${({ theme }) => css`
		min-height: ${theme.size.SIZE_700};

		padding: ${theme.size.SIZE_006};

		border: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};
		border-radius: 0 ${theme.size.SIZE_004} ${theme.size.SIZE_004} ${theme.size.SIZE_004};

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: 90%;
		}
	`}
`;
