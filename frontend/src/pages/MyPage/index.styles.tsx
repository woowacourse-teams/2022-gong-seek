import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100%;
`;

export const Title = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_018};
	color: ${({ theme }) => theme.colors.PURPLE_500};

	margin-bottom: ${({ theme }) => theme.size.SIZE_020};
	padding-left: ${({ theme }) => theme.size.SIZE_010};
`;

export const ContentContainer = styled.div`
	display: flex;
	flex-direction: column;

	gap: ${({ theme }) => theme.size.SIZE_040};
`;
